(function ($) {
    /*
     * 自定义jquery函数，完成将form 数据转换为 json格式
     */
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        $(array).each(
            function () {
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [
                            serializeObj[this.name], this.value];
                    }
                } else {
                    serializeObj[this.name] = this.value;
                }
            });
        return serializeObj;
    };

    var DEFAULT = {
        url: "",
        dataType: "json",
        contentType: 'application/json',
        type: "POST",
        data: JSON.stringify({}),
        success: function (result) {

        },
        error: function (jqXHR, textStatus, errorThrown) {
            var response = jQuery.parseJSON(jqXHR.responseText);
            Bright.showErrMesg("系统错误：" + response.status + "," + response.message);
        }
    };

    $.fn.valiateAndSubmit = function (options) {
        var settings = $.extend(DEFAULT, options);
        //$(this).validator();
        $(this).validator('validate');
        $(this).validator().on('submit', function (e) {
            if (e.isDefaultPrevented()) {
                //form invalid
                return false;
            } else {
                e.preventDefault();
                var data = $(this).serializeJson();
                $.ajax(settings);
            }
        });
        $(this).submit();

        return this.each(function () {
        });
    };
})(jQuery)
