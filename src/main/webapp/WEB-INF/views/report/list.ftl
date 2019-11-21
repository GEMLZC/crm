<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>部门管理</title>
    <#include "../common/link.ftl"/>
    <script>
        $(function () {
            $('.chart_btn').click(function () {
                // 清除模态框缓存
                $('#myModal').removeData("bs.modal");
                var url = $(this).data('url')+"?"+$('#searchForm').serialize();
                console.log(url);
                //打开模态框并远程加载数据
                $('#myModal').modal({
                    remote:url,
                    show:true
                });
            })
        })

        $(function () {
            //提交表单
            $('#save').click(function () {
                //点击之后会变成异步表单并马上提交
                $('#editForm').ajaxSubmit(function (data) {
                    //调用提示函数
                    message(data);
                })
            })
        })

    </script>
    <script src="/js/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl"/>
    <!--菜单回显-->
    <#assign currentMenu="customerReport"/>
    <#include "../common/menu.ftl"/>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>潜在客户报表</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/customerReport/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <div class="form-group">
                        <label for="keyword">员工姓名:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!""}" placeholder="请输入姓名">
                    </div>
                    <div class="form-group">
                        <label for="date">时间段查询:</label>
                        <input type="text" class="form-control" style="width: 100px" id="beginTime" name="beginTime"   value="${(qo.beginTime?string('yyyy-MM-dd'))!""}"> -
                        <input type="text" class="form-control" style="width: 100px" id="endTime" name="endTime"  value="${(qo.endTime?string('yyyy-MM-dd'))!""}">
                    </div>
                    <script>
                        $('#beginTime').click(function () {
                            WdatePicker({
                                readOnly:true,
                                maxDate:new Date()
                            });
                        })
                        $('#endTime').click(function () {
                            WdatePicker({
                                readOnly:true,
                                maxDate:new Date(),
                                minDate:$('#beginTime').val()
                            });
                        })
                    </script>
                    <div class="form-group">
                        <label for="status">分组类型:</label>
                        <select class="form-control" id="groupType" name="groupType">
                            <option value="e.name">员工</option>
                            <option value="DATE_FORMAT(c.input_time,'%Y')">年</option>
                            <option value="DATE_FORMAT(c.input_time,'%Y-%m')">月</option>
                            <option value="DATE_FORMAT(c.input_time,'%Y-%m-%d')">日</option>
                        </select>
                        <script>
                            $('#groupType').val("${qo.groupType!}");
                        </script>
                        <button id="btn_query" class="btn btn-primary">
                            <span class="glyphicon glyphicon-search"></span> 查询
                        </button>
                        <button type="button" class="btn btn-info chart_btn" data-url="/customerReport/chartByBar.do">
                            <span class="glyphicon glyphicon-stats"></span> 柱状图
                        </button>
                        <button type="button" class="btn btn-warning chart_btn" data-url="/customerReport/chartByPie.do">
                            <span class="glyphicon glyphicon-dashboard"></span> 饼状图
                        </button>
                    </div>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>分组类型</th>
                            <th>潜在客户新增数</th>
                        </tr>
                        </thead>
                   <#list result as entity>
                       <tr>
                           <td>${entity.groupType}</td>
                           <td>${entity.customerNum}</td>
                       </tr>
                   </#list>
                    </table>
                </div>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl"/>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>

</body>
</html>
