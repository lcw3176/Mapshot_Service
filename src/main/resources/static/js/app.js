window.addEventListener("load", function () {
    var naverProfile = new mapshot.profile.Naver();
    var proxyProfile = new mapshot.profile.Proxy();
    naverProfile.setKey("ny5d4sdo0e");
    proxyProfile.setProxyUrl("/map/storage")

    var coor = new mapshot.coors.LatLng();
    var naverTile = new mapshot.maps.NaverTile();
    var proxyTile = new mapshot.maps.ProxyTile();

    var kakaoMaps = new KakaoMap();
    var rectangle = null;
    var traceMode = false;
    var resultType = null;
    var mapRadius = null;
    var url;
    var progressBar = document.getElementById("progressBar");

    var isRun = false;
    var expectedEndTime = null;

    // 카카오 지도 설정

    document.getElementById("searchPlaces").onsubmit = function () {
        kakaoMaps.searchPlaces();
        return false;
    };

    kakao.maps.event.addListener(kakaoMaps.getMap(), 'click', function (mouseEvent) {
        coor.init(mouseEvent.latLng.getLat(), mouseEvent.latLng.getLng());
        document.getElementById("lat").innerText = coor.getY();
        document.getElementById("lng").innerText = coor.getX();

        if (rectangle != null) {
            rectangle.setMap(null);
        }

        naverTile.setLevel(mapRadius);
        proxyProfile.setCenter(coor);
        var sw = naverTile.getSW(mapRadius, coor);
        var ne = naverTile.getNE(mapRadius, coor);
        rectangle = new kakao.maps.Rectangle({
            bounds: new kakao.maps.LatLngBounds(new kakao.maps.LatLng(sw.getY(), sw.getX()), new kakao.maps.LatLng(ne.getY(), ne.getX())),
            strokeWeight: 4,
            strokeColor: '#FF3DE5',
            strokeOpacity: 1,
            strokeStyle: 'shortdashdot',
            fillColor: '#FF8AEF',
            fillOpacity: 0.8
        });
        rectangle.setMap(kakaoMaps.getMap());
    });

    document.getElementById("map").onmousedown = function (e) {
        if (e.button === 2 && rectangle != null) {
            rectangle.setMap(null);
        }
    };
    // 카카오 지도 설정 끝


    // 맵샷 네이버 이벤트 리스너 정의
    document.body.addEventListener("naverTileOnLoadStart", function (e) {
        progressBar.max = e.detail.total;
        progressBar.value = 0;
    });
    document.body.addEventListener("naverTileOnProgress", function (e) {
        progressBar.value += 1;
        document.getElementById("captureStatus").innerText = progressBar.value + "/" + progressBar.max + " 수집 완료";
    });
    document.body.addEventListener("naverTileOnError", function (e) {
        progressBar.value += 1;
        progressBar.setAttribute("class", "progress is-danger");
        document.getElementById("captureStatus").innerText = progressBar.value + "/" + progressBar.max + " 수집 완료";
    }); // 네이버 이벤트 리스너 정의 끝


    // 맵샷 프록시 이벤트 리스너 정의
    document.body.addEventListener("proxyTileOnError", function (e) {
        document.getElementById("captureStatus").innerText = "서버 에러입니다. 잠시 후 다시 시도해주세요.";
        progressBar.setAttribute("class", "progress is-danger");
        progressBar.setAttribute("value", 100);
        isRun = false;
    });

    // 맵샷 프록시 이벤트 리스너 정의 끝


    setZoomLevel = function (_km, id) {
        var matches = document.getElementsByClassName("zoom");

        for (var i = 0; i < matches.length; i++) {
            matches[i].setAttribute('class', 'zoom');
        }

        switch (_km) {
            case 1:
                mapRadius = mapshot.radius.One;
                break;

            case 2:
                mapRadius = mapshot.radius.Two;
                break;

            case 5:
                mapRadius = mapshot.radius.Five;
                break;

            case 10:
                mapRadius = mapshot.radius.Ten;
                break;

            default:
                break;
        }

        id.setAttribute('class', 'zoom is-active');
    };

    setBaseMap = function (mapType, id) {
        var matches = document.getElementsByClassName("map");

        for (var i = 0; i < matches.length; i++) {
            matches[i].setAttribute('class', 'map');
        }

        naverProfile.setMapType(mapType);
        proxyProfile.setMapType(mapType);
        id.setAttribute('class', 'map is-active');
    };

    setCompany = function (companyName) {
        if (proxyProfile.isLayerMode() && (companyName === "naver" || companyName === "google")) {
            alert("지적 편집도는 카카오 지도만 사용 가능합니다");
            return;
        }

        var matches = document.getElementsByClassName("company");

        for (var i = 0; i < matches.length; i++) {
            matches[i].setAttribute('class', 'company');
        }

        if (companyName === "kakao") {
            document.getElementById("kakao").setAttribute("class", "company button is-warning");
        } else if (companyName === "naver") {
            document.getElementById("naver").setAttribute("class", "company button is-success");
        } else if (companyName === "google") {
            document.getElementById("google").setAttribute("class", "company button is-link");
        }

        resultType = companyName;
        proxyProfile.setCompanyType(companyName);
    };

    setTraceMode = function (id) {
        if (id.getAttribute("class") !== "is-active") {
            id.setAttribute("class", "is-active");
            traceMode = true;
        } else {
            id.setAttribute("class", "");
            traceMode = false;
        }
    };

    setLayerMode = function (id) {
        if (id.getAttribute("class") !== "is-active") {
            id.setAttribute("class", "is-active");
            proxyProfile.setLayerMode(true);
            setCompany("kakao");
        } else {
            id.setAttribute("class", "");
            proxyProfile.setLayerMode(false);
        }
    };

    startCapture = function () {
        if (url != null) {
            URL.revokeObjectURL(url);
        }

        if (coor.getX() === undefined || coor.getY() === undefined) {
            alert("좌표 설정을 먼저 진행해 주세요.");
            return;
        }

        if (resultType == null) {
            alert("출력 타입을 지정해주세요");
            return;
        }

        if (isRun) {
            alert("이미지 요청 중입니다. 잠시만 기다려 주세요");
            return;
        }

        if (traceMode) {
            var traceRec = new kakao.maps.Rectangle({
                bounds: rectangle.getBounds(),
                strokeWeight: 4,
                strokeColor: '#000000',
                strokeOpacity: 1,
                strokeStyle: 'shortdot',
                fillColor: '#ecf4f3',
                fillOpacity: 0.8
            });
            traceRec.setMap(kakaoMaps.getMap());
        }

        if (resultType === "kakao" || resultType === "google") {
            proxyCapture();
        } else if (resultType === "naver") {
            naverCapture();
        }
    };

    function proxyCapture() {
        var defaultBlockSize = 1000;
        var googleOffset = 500;
        proxyProfile.setRadius(mapRadius);
        var fileName = document.getElementById("bunzi-address").innerText;
        progressBar.removeAttribute("value");
        progressBar.setAttribute("class", "progress is-warning");
        document.getElementById("captureStatus").innerText = "서버에 요청중입니다. 잠시 기다려주세요";
        isRun = true;

        var canvas = document.createElement("canvas");

        if(proxyProfile.getCompanyType() === "google"){
            canvas.width = proxyProfile.getWidth() - googleOffset;
            canvas.height = proxyProfile.getWidth() - googleOffset;
        } else {
            canvas.width = proxyProfile.getWidth();
            canvas.height = proxyProfile.getWidth();
        }


        var ctx = canvas.getContext("2d");
        var sideBlockCount = parseInt(proxyProfile.getWidth() / defaultBlockSize);
        var maxCount = sideBlockCount * sideBlockCount;
        var count = 0;
        var sock = new SockJS("/map/register");
        var stompClient = Stomp.over(sock);
        stompClient.debug = null;
        stompClient.connect({}, function(){

            var sessionId = stompClient.ws._transport.url;
            sessionId = sessionId.replace(
                "wss://kmapshot.com/map/register/",  "");

            sessionId = sessionId.replace(
                "wss://www.kmapshot.com/map/register/",  "");

            sessionId = sessionId.replace("/websocket", "");
            sessionId = sessionId.replace(/^[0-9]+\//, "");

            stompClient.subscribe("/topic/waiter", function(message){
                var json = JSON.parse(message.body);

                if(json.index === 0 && json.sessionId === sessionId){
                    expectedEndTime = new Date();
                    expectedEndTime.setMinutes(expectedEndTime.getMinutes() + 1);

                    document.getElementById("captureStatus").innerText =
                        "지도 생성중 입니다. 예상 완료시간 -> " + expectedEndTime.toLocaleTimeString();
                    progressBar.setAttribute("class", "progress is-info");

                } else if(json.index > 0 && json.sessionId === sessionId){
                    document.getElementById("captureStatus").innerText =
                        json.index + " 명의 유저가 생성 대기중 입니다. 예상 대기시간: " + json.index + "분";
                }


            })

            stompClient.subscribe("/queue/task/" + sessionId, function(message){
                var json = JSON.parse(message.body);

                if(count === 0){
                    progressBar.max = 100;
                    document.getElementById("captureStatus").innerText = 0 + " / 100";
                    progressBar.setAttribute("value", 0);
                }

                if(json.error){
                    document.getElementById("captureStatus").innerText = "서버 에러입니다. 잠시 후 다시 시도해주세요.";
                    progressBar.setAttribute("class", "progress is-danger");
                    progressBar.setAttribute("value", 100);
                    isRun = false;
                    expectedEndTime = null;
                    sock.close();
                }


                proxyTile.requestImage(proxyProfile, json.uuid, function (loadedImage){
                    if(proxyProfile.getCompanyType() === "google"
                        && json.x + defaultBlockSize === sideBlockCount * defaultBlockSize
                        && json.y + defaultBlockSize !== sideBlockCount * defaultBlockSize){

                        ctx.drawImage(loadedImage, 0, 0, loadedImage.width, loadedImage.height,
                            json.x - googleOffset, json.y, defaultBlockSize, defaultBlockSize);
                    } else if(proxyProfile.getCompanyType() === "google"
                        && json.x + defaultBlockSize !== sideBlockCount * defaultBlockSize
                        && json.y + defaultBlockSize === sideBlockCount * defaultBlockSize) {

                        ctx.drawImage(loadedImage, 0, 0, loadedImage.width, loadedImage.height,
                            json.x, json.y - googleOffset, defaultBlockSize, defaultBlockSize);
                    } else if(proxyProfile.getCompanyType() === "google"
                        && json.x + defaultBlockSize === sideBlockCount * defaultBlockSize
                        && json.y + defaultBlockSize === sideBlockCount * defaultBlockSize) {

                        ctx.drawImage(loadedImage, 0, 0, loadedImage.width, loadedImage.height,
                            json.x - googleOffset, json.y - googleOffset, defaultBlockSize, defaultBlockSize);
                    } else {

                        ctx.drawImage(loadedImage, 0, 0, loadedImage.width, loadedImage.width,
                            json.x, json.y, defaultBlockSize, defaultBlockSize);
                    }

                    count++;
                    document.getElementById("captureStatus").innerText = parseInt((count / maxCount) * 100).toString() + " / 100";
                    progressBar.setAttribute("value", (count / maxCount * 100).toString());

                    if(count === maxCount){
                        canvasToFile(canvas, fileName);
                        progressBar.setAttribute("value", 100);
                        isRun = false;
                        expectedEndTime = null;
                        sock.close();
                    }
                })
            })

            stompClient.send("/map/task", {}, proxyProfile.getParamsToJson());
            stompClient.send("/map/waiter", {}, "");
        });
    }

    function naverCapture() {
        isRun = true;
        progressBar.setAttribute("class", "progress is-info");
        naverProfile.setLevel(mapRadius);
        var fileName = document.getElementById("bunzi-address").innerText;

        naverTile.draw(coor, mapRadius, naverProfile, function (canvas) {
            canvasToFile(canvas, fileName);
            isRun = false;
        });
    }

    function canvasToFile(canvas, fileName){
        if (canvas.msToBlob) {
            canvas.toBlob(function (blob) {
                navigator.msSaveBlob(blob, "mapshot_" + fileName + ".jpg");
                document.getElementById("captureStatus").innerText = "완료되었습니다.";
            }, "image/jpeg");
        } else {
            canvas.toBlob(function (blob) {
                url = URL.createObjectURL(blob);
                var tag = document.getElementById("resultHref");
                tag.href = url;
                tag.download = "mapshot_" + fileName + ".jpg";
                document.getElementById("resultSpan").innerHTML = "mapshot_" + fileName + ".jpg";
                document.getElementById("captureStatus").innerText = "완료되었습니다. 생성된 링크를 확인하세요";
            }, "image/jpeg");
        }
    }


    document.getElementById("default_click_level").click();
    document.getElementById("default_click_map").click();
    document.getElementById("setTrace").click();
});
