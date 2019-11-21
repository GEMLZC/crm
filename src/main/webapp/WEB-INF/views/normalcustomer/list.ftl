<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl" />
    <script src="/js/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl" />
    <!--菜单回显-->
    <#assign currentMenu="normalCustomer"/>
    <#include "../common/menu.ftl" />
    <div class="content-wrapper">
        <section class="content-header">
            <h1>正式客户管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <form class="form-inline" id="searchForm" action="/normalCustomer/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <div class="form-group">
                            <label for="keyword">关键字:</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}"
                                   placeholder="请输入姓名">
                        </div>

                            <div class="form-group">
                                <label for="seller"> 销售课程:</label>
                                <select class="form-control" id="course" name="courseId">
                                    <option value="-1">全部</option>
                                        <#list courses as s>
                                            <option value="${s.id!}">${s.title!}</option>
                                        </#list>
                                </select>
                                <script>
                                    $("#course option[value='${qo.courseId!}']").prop("selected", true);
                                    <#--$("#dept option").val('${qo.deptId}');-->
                                </script>
                            </div>
                        <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>
                            查询
                        </button>
                            <a href="#" class="btn btn-success btn_redirect btn-input">
                                <span class="glyphicon glyphicon-plus"></span> 添加
                            </a>
                    </form>
                </div>
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>客户名称</th>
                        <th>销售课程</th>
                        <th>销售时间</th>
                        <th>销售金额</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <#--判断result是否存在-->
                    <#if result??>
                        <#list result.list as entity>
                        <tr>
                            <td>${entity_index+1}</td>
                            <td>${(entity.customer.name)!}</td>
                            <td>${(entity.course.title)!}</td>
                            <td>${(entity.inputTime?string('yyyy-MM-dd'))!}</td>
                            <td>${entity.money!}</td>
                            <td>
                                <a href="#" class="btn btn-info btn-input btn-xs btn_redirect" data-json='${entity.json!}'>
                                    <span class="glyphicon glyphicon-pencil"></span> 编辑
                                </a>
                            </td>
                        </tr>
                        </#list>
                    </#if>
                </table>
                <!--分页-->
                <#include "../common/page.ftl" />
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl" />
</div>
<script>
    //新增、编辑
    $(function () {
        //客户编辑模态框
        $('.btn-input').click(function () {
            $('#statusModal').modal('show');
        })

        //提交表单
        $('.statusSubmit').click(function () {
            //点击之后会变成异步表单并马上提交
            $('#statusForm').ajaxSubmit(message)
        })

        /*//客户编辑模态框回显
        $('.btn-input').click(function () {
            //每次点击先清除模态框的数据，保证添加数据数据的是空
            $('#editForm input').val('');
            //拿到自定义属性的值
            var json = $(this).data('json');
            if (json != null){
                //给模态框设置值，做编辑回显
                $('#editModal input[name=name]').val(json.name);
                $('#editModal input[name=age]').val(json.age);
                $('#editModal input[name=id]').val(json.id);
                $('#editModal input[name=gender]').val(json.gender);
                $('#editModal input[name=tel]').val(json.tel);
                $('#editModal input[name=qq]').val(json.qq);
                $("#editModal select[name='job.id']").val(json.jobId);
                $("#editModal select[name='source.id']").val(json.sourceId);
            }
        })*/

    })
</script>

<#--新增/编辑-->
<div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">修改客户状态</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/normalCustomer/save.do" method="post" id="statusForm">
                    <input type="hidden" name="id" value=""/>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">选择客户：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="customer.id">
                                        <#list customers as s>
                                            <option value="${s.id!}">${s.name!}</option>
                                        </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">选择课程：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="course.id">
                                        <#list courses as s>
                                            <option value="${s.id!}">${s.title!}</option>
                                        </#list>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" >
                        <label class="col-lg-4 control-label">金额：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" name="money"
                                   placeholder="请输入客户名称"/>

                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary statusSubmit">保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
