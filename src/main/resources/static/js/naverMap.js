class NaverMap {
    constructor() {
        this.map = null;
        this.init();
    }

    init(){
        if(this.map == null){
            this.map = new naver.maps.Map('map', {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoom: 10
            });
        }
    }

    getMap(){
        return this.map;
    }

    // searchPlaces(address) {
    //     var point;
    //     naver.maps.Service.geocode({
    //         query: address
    //     }, function(status, response) {
    //         if (status === naver.maps.Service.Status.ERROR) {
    //             if (!address) {
    //                 return alert('Geocode Error, Please check address');
    //             }
    //             return alert('Geocode Error, address:' + address);
    //         }
    //
    //         if (response.v2.meta.totalCount === 0) {
    //             return alert('No result.');
    //         }
    //
    //         var item = response.v2.addresses[0];
    //         point = new naver.maps.Point(item.x, item.y);
    //         document.getElementById("lat").innerText = item.y;
    //         document.getElementById("lng").innerText = item.x;
    //
    //         if (item.roadAddress) {
    //             document.getElementById("load-address").innerText = item.roadAddress;
    //         }
    //
    //         if (item.jibunAddress) {
    //             document.getElementById("bunzi-address").innerText = item.jibunAddress;
    //         }
    //     });
    //
    // }


    searchCoordinateToAddress(latlng) {

        naver.maps.Service.reverseGeocode({
            coords: latlng,
            orders: [
                naver.maps.Service.OrderType.ADDR,
                naver.maps.Service.OrderType.ROAD_ADDR
            ].join(',')
        }, function(status, response) {
            if (status === naver.maps.Service.Status.ERROR) {
                if (!latlng) {
                    return alert('ReverseGeocode Error, Please check latlng');
                }
                if (latlng.toString) {
                    return alert('ReverseGeocode Error, latlng:' + latlng.toString());
                }
                if (latlng.x && latlng.y) {
                    return alert('ReverseGeocode Error, x:' + latlng.x + ', y:' + latlng.y);
                }
                return alert('ReverseGeocode Error, Please check latlng');
            }

            var address = response.v2.address;

            document.getElementById("bunzi-address").innerText = address.jibunAddress;
            document.getElementById("load-address").innerText = address.roadAddress;

        });
    }
}