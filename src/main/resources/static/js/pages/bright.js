(function ($) {

    function getTree() {
        var levels = $.ajax({url: "/testCaseList", async: false}).responseJSON;
        var tree = [];
        tree.push({"text": "新增用例", "href": "javascript:Bright.loadPage('addTestcase');"});
        tree.push({"text": "测试计划", "href": "javascript:Bright.loadPage('editSchedule');"});
        tree.push(levels);
        return tree;
    }
    
    $.fn.loadTree = function() {
      $('#tree').treeview({data: getTree(), showBorder: false,color: "#a7b1c2",backColor: "#2f4050", enableLinks: true, showTags:true});
    };
    
    /**
     * 加载页面
     * @param url
     */
    $.fn.loadPage = function (url) {
        if (!url || url == '#') return false;
        if ($("a[href*='" + url + "']").html() && url.indexOf("editTestCase?caseName") != -1) {
          Bright.node = $("a[href*='" + url + "']").parent();
        }
        $(".wrapper-content").empty();
        $(".wrapper-content").load(url, function (response, status, xhr) {
            if ("error" == status) {
                var message = "系统错误：" + $(response).find('.error-status').html() || '' + " , " + $(response).find('.error-message').html();
                Bright.showErrMesg(message);
            } else {
                setTimeout(Bright.initBreadcrumb(),500);
                $("form").bind("keyup keypress", function (e) {
                    if (e.keyCode == 13) {
                        return false;
                    }
                });
            }
        });
    };

    /**
     * 注册导航点击事件
     */
    $.fn.initBreadcrumb = function () {
        $(".breadcrumb-link").each(function (index, link) {
            $(link).click(function (e) {
                e.preventDefault();
                //加载功能页
                var url = Bright.contextPath+$(link).attr('path');
                Bright.loadPage(url);
            });
        });
    };

    /**
     * 显示错误信息
     * @param message
     */
    $.fn.showErrMesg = function (message) {
        $('.alert-info').removeClass('hide');
        $('.alert-info').html(message);
        setTimeout(function () {
            $('.alert-info').addClass('hide');
        }, 5000);

    };
})(jQuery);

var Bright = window.Bright = $('#wrapper');
//Bright.contextPath = $("meta[name='ctx']").attr("content");
Bright.contextPath = "";