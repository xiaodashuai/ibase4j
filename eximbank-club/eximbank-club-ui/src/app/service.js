var app = angular.module("app");


//公共服务

app.service('$CanvasService', ['$rootScope','$q', function ($rootScope,$q) {

    //创建快照
    this.createCanvas = function (doc,type,num,bizcode,extra,$scope,tab) {

        var shareContent = doc;//需要截图的包裹的（原生的）DOM 对象
        var width = shareContent.offsetWidth; //获取dom 宽度
        var height = shareContent.offsetHeight; //获取dom 高度
        var canvas = document.createElement("canvas"); //创建一个canvas节点
        var scale = 1; //定义任意放大倍数 支持小数
        canvas.width = width * scale; //定义canvas 宽度 * 缩放
        canvas.height = height * scale; //定义canvas高度 *缩放
        canvas.getContext("2d").scale(scale, scale); //获取context,设置scale

        //参数配置
        var options = {
            scale: scale, // 添加的scale 参数
            canvas: canvas, //自定义 canvas
            logging: false, //日志开关，便于查看html2canvas的内部执行流程
            width: width, //dom 原始宽度
            height: height,
            useCORS: true // 【重要】开启跨域配置
        };

        var defer = $q.defer();

       html2canvas(doc, options).then(function (canvas) {

           var canvasImage = canvas.toDataURL("image/png");
            $.ajax({
                url: '/eximbank-club/bizUtil/read/savCanvas',
                type: 'POST',
                async: false,
                data: angular.toJson({
                    imgStr: canvasImage.toString(),
                    type:type,
                    num:num,
                    bizcode:bizcode,
                    extra:extra,
                }),
                success: function (res) {
                    if(res == true){
                        defer.resolve(res);
                    }else{
                        defer.reject(res);
                    }
                },
                error: function (res) {
                    defer.reject(res);
                }
            });
        });
       return defer.promise;
    };

    //读取快照
    this.readCanvas = function (type,num,bizcode,extra) {

        var confirmContent = [];
        var deferred = $q.defer();

        $.ajax({
            url : '/eximbank-club/bizUtil/read/readCanvas',
            type: 'POST',
            //async: false,
            data: angular.toJson({
                type:type,
                num:num,
                bizcode:bizcode,
                extra:extra
            })
        }).then(function(result) {
            confirmContent = result;
            deferred.resolve(confirmContent);
        });
        return deferred.promise;
    };

}]);


app.directive('draggAble', ['$document', function ($document) {
    //弹窗支持拖动
    return function (scope, element, attr) {
        var startX = 0, startY = 0, x = 0, y = 0;
        element = angular.element(document.getElementsByClassName("modal-dialog"));
        element.css({
            position: 'relative',
            cursor: 'move'
        });

        element.on('mousedown', function (event) {
            // Prevent default dragging of selected content
//          event.preventDefault();
            startX = event.pageX - x;
            startY = event.pageY - y;
            $document.on('mousemove', mousemove);
            $document.on('mouseup', mouseup);
        });

        function mousemove(event) {
            y = event.pageY - startY;
            x = event.pageX - startX;
            element.css({
                top: y + 'px',
                left: x + 'px'
            });
        }
        function mouseup() {
            $document.off('mousemove', mousemove);
            $document.off('mouseup', mouseup);
        }
    };
}]).directive('clickAndDisable', function () {
    //点击事件后按钮灰显
    return {
        scope: {
            clickAndDisable: '&'
        },
        link: function (scope, iElement, iAttrs) {
            iElement.bind('click', function () {
                iElement.prop('disabled', true);
                scope.clickAndDisable().finally(function () {
                    iElement.prop('disabled', false);
                })
            });
        }
    };
});
