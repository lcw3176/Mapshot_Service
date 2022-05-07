window.addEventListener("load", function () {
    var naverProfile = new mapshot.profile.Naver();
    var proxyProfile = new mapshot.profile.Proxy();
    naverProfile.setKey("ny5d4sdo0e");
    proxyProfile.setProxyUrl("/map/storage")

    var coor = new mapshot.coors.LatLng();
    var naverTile = new mapshot.maps.NaverTile();
    var proxyTile = new mapshot.maps.ProxyTile();

    var map = new Map();
    var rectangle = null;
    var traceMode = false;
    var resultType = null;
    var mapRadius = null;
    var url;
    var progressBar = document.getElementById("progressBar");

    var isProxyRun = false;
    var expectedEndTime = null;

    // 카카오 지도 설정

    document.getElementById("searchPlaces").onsubmit = function () {
        map.searchPlaces();
        return false;
    };

    kakao.maps.event.addListener(map.getMap(), 'click', function (mouseEvent) {
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
        rectangle.setMap(map.getMap());
    });

    document.getElementById("map").onmousedown = function (e) {
        if (e.button == 2 && rectangle != null) {
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

    document.body.addEventListener("proxyTileOnProgress", function (e) {
        progressBar.max = 100;
        document.getElementById("captureStatus").innerText = e.detail.percentage + " / 100";
        progressBar.setAttribute("value", e.detail.percentage);
    });
    document.body.addEventListener("proxyTileOnError", function (e) {
        document.getElementById("captureStatus").innerText = "서버 에러입니다. 잠시 후 다시 시도해주세요.";
        progressBar.setAttribute("class", "progress is-danger");
        progressBar.setAttribute("value", 100);
        isProxyRun = false;
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

        proxyProfile.setLevel(_km);
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
        if (id.getAttribute("class") != "is-active") {
            id.setAttribute("class", "is-active");
            traceMode = true;
        } else {
            id.setAttribute("class", "");
            traceMode = false;
        }
    };

    setLayerMode = function (id) {
        if (id.getAttribute("class") != "is-active") {
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

        if (coor.getX() == undefined || coor.getY() == undefined) {
            alert("좌표 설정을 먼저 진행해 주세요.");
            return;
        }

        if (resultType == null) {
            alert("출력 타입을 지정해주세요");
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
            traceRec.setMap(map.getMap());
        }

        if (resultType === "kakao" || resultType === "google") {
            proxyCapture();
        } else if (resultType === "naver") {
            naverCapture();
        }
    };

    function proxyCapture() {

        if (isProxyRun) {
            alert("이미지 요청 중입니다. 잠시만 기다려 주세요");
            return;
        }

        var fileName = document.getElementById("bunzi-address").innerText;
        progressBar.removeAttribute("value");
        progressBar.setAttribute("class", "progress is-warning");
        document.getElementById("captureStatus").innerText = "서버에 요청중입니다. 잠시 기다려주세요";
        isProxyRun = true;

        var sock = new SockJS("/map/register");
        sock.onopen = function() {
            sock.send(proxyProfile.getParamsToJson());
        };

        sock.onmessage = function(message) {
            var json = JSON.parse(message.data);

            if(json.done){
                sock.close();

                proxyTile.requestImage(proxyProfile, json.uuid, function (blob){

                    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                        navigator.msSaveBlob(blob, "mapshot_" + fileName + ".jpg");
                        document.getElementById("captureStatus").innerText = "완료되었습니다.";
                    } else {
                        url = URL.createObjectURL(blob);
                        var tag = document.getElementById("resultHref");
                        tag.href = url;
                        tag.download = "mapshot_" + fileName + ".jpg";
                        var span = document.getElementById("resultSpan");
                        span.innerHTML = "mapshot_" + fileName + ".jpg";
                        document.getElementById("captureStatus").innerText = "완료되었습니다. 생성된 링크를 확인하세요";
                    }

                    progressBar.setAttribute("value", 100);
                    isProxyRun = false;
                    expectedEndTime = null;
                });

            } else {
                if(json.index === 0 && expectedEndTime == null){
                    expectedEndTime = new Date();
                    expectedEndTime.setMinutes(expectedEndTime.getMinutes() + 1);

                    document.getElementById("captureStatus").innerText =
                        "지도 생성중 입니다. 예상 완료시간 -> " + expectedEndTime.toLocaleTimeString();
                    progressBar.setAttribute("class", "progress is-info");

                } else {
                    document.getElementById("captureStatus").innerText =
                        json.index + " 명의 유저가 생성 대기중 입니다. 예상 대기시간: " + (json.index + 1) + "분";
                }
            }
        };
    }

    function naverCapture() {
        progressBar.setAttribute("class", "progress is-info");
        naverProfile.setLevel(mapRadius);
        naverTile.draw(coor, mapRadius, naverProfile, function (canvas) {
            var fileName = document.getElementById("bunzi-address").innerText;

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
        });
    }


    document.getElementById("default_click_level").click();
    document.getElementById("default_click_map").click();
    document.getElementById("setTrace").click();
});