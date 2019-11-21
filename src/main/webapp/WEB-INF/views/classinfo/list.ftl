<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>班级管理</title>
    <#include "../common/link.ftl"/>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl"/>
    <!--菜单回显-->
    <#assign currentMenu="classinfo"/>
    <#include "../common/menu.ftl"/>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>班级管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/class/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}"
                               placeholder="请输入班级/人数">
                    </div>
                    <div class="form-group">
                        <label for="dept"> 班主任:</label>
                        <select class="form-control" id="dept" name="empId">
                            <option value="-1">全部</option>
                                    <#list emps as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                        </select>
                        <script>
                            $("#dept option[value='${qo.empId!}']").prop("selected", true);
                            <#--&lt;#&ndash;$("#dept option").val('${qo.deptId}');&ndash;&gt;-->
                        </script>
                    </div>
                    <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>
                        查询
                    </button>
                    <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                        <span class="glyphicon glyphicon-plus"></span> 添加
                    </a>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>班级名称</th>
                            <th>班级人数</th>
                            <th>班主任</th>
                            <th>操作</th>
                        </tr>
                        <!--判断result是否存在-->
                        <#if result??>
                            <#list result.list as entity>
                                <tr>
                                    <td>${entity_index+1!}</td>
                                    <td>${entity.name!}</td>
                                    <td>${entity.number!}</td>
                                    <td>${entity.emp.name!}</td>
                                    <td>
                                        <a href="#" class="btn btn-info btn-xs btn-input" id="a" data-json='${entity.json!}'>
                                            <span class="glyphicon glyphicon-pencil"></span> 编辑
                                        </a>
                                        <a href="javascript:remove('${entity.id}')"
                                           class="btn btn-danger btn-xs btn-delete">
                                            <span class="glyphicon glyphicon-trash"></span> 删除
                                        </a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </table>
                    <script>
                        //删除
                        function remove(id) {
                            $.messager.model = {
                                ok: {text: "确定"},
                                cancel: {text: "取消"}
                            };
                            $.messager.confirm('温馨提示', '确认是否删除？', function () {
                                $.get('/class/remove.do?id=' + id, function (data) {
                                    if (data.success){
                                        location.href = '/class/list.do';
                                    }else {
                                        alert(data.message);
                                    }
                                })
                            })

                        }

                        //新增、编辑
                        $(function () {
                            $('.btn-input').click(function () {
                                //每次点击先清除模态框的数据，保证添加数据数据的是空
                                $('#name').val('');
                                $('#number').val('');
                                $('#id').val('');
                                //拿到自定义属性的值
                                var json = $(this).data('json');
                                //给模态框设置值，做编辑回显
                                $('#name').val(json.name);
                                $('#number').val(json.number);
                                $('#id').val(json.id);
                                var empId = json.empId;
                                console.log(empId);
                                $("#empid option[value='"+empId+"']").prop('selected',true);

                            })
                        })

                    </script>
                    <!--分页-->
                    <#include "../common/page.ftl"/>
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
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">班级信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/class/save.do" method="post" id="editForm">
                    <input type="hidden" value="" name="id" id="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">班级名称：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="name" name="name" value=""
                                   placeholder="请输入班级名称">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">班级人数：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="number" name="number" value=""
                                   placeholder="请输入班级人数">
                        </div>
                    </div>

                    <div class="form-group" style="margin-top: 10px;">
                        <label for="dept"> 班主任:</label>
                            <select class="form-control" id="empid" name="emp.id" >
                                        <#list emps as d>
                                            <option value="${d.id!}" >${d.name!}</option>
                                        </#list>
                            </select>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script>
    $('.btn-input').click(function () {
        $('#myModal').modal('show');
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
</body>
</html>
