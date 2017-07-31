app.controller("IndexController", ["$scope", '$resource', function ($scope, $resource) {
    "use strict";

    $scope.msg = "";
    $scope.imageSrc = "/images/dot.png";

    $scope.init = function() {
    };

    $scope.moveLeft = function() {
        move("left");
    };

    $scope.moveRight = function() {
        move("right");
    };

    $scope.moveUp = function() {
        move("up");
    };

    $scope.moveDown = function() {
        move("down");
    };


    function move(direction) {
        $scope.msg = "moving " + direction;

        $resource('/move', {}).get({
            direction: direction,
            duration: 1000},
            function success(x) {
                if (!x.success) {
                    $scope.msg = x.message;

                } else {
                    $scope.msg = "ok";
                    $scope.imageSrc = x.imageURL;
                }
            },
            function error(x) {
                $scope.msg = "server backend error";
        });
    }

    move(" at initial");
}]);