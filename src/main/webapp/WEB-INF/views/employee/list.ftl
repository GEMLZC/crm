<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl" />
    <!--菜单回显-->
    <#assign currentMenu="employee"/>
    <#include "../common/menu.ftl" />
    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <form class="form-inline" id="searchForm" action="/emp/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <div class="form-group">
                            <label for="keyword">关键字:</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}"
                                   placeholder="请输入姓名/邮箱">
                        </div>
                        <div class="form-group">
                            <label for="dept"> 部门:</label>
                            <select class="form-control" id="dept" name="deptId">
                                <option value="-1">全部</option>
                                    <#list depts as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                            </select>
                            <script>
                                $("#dept option[value='${qo.deptId!}']").prop("selected", true);
                                <#--$("#dept option").val('${qo.deptId}');-->
                            </script>
                        </div>
                        <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>
                            查询
                        </button>
                        <@shiro.hasPermission name="employee:input">
                            <a href="/emp/input.do" class="btn btn-success btn_redirect">
                                <span class="glyphicon glyphicon-plus"></span> 添加
                            </a>
                        </@shiro.hasPermission>
                        <a href="javascript:batchRemove()" class="btn btn-danger btn_delete">
                            <span class="glyphicon glyphicon-trash"></span> 批量删除
                        </a>
                        <a href="/emp/exportXls.do" target="_blank" class="btn btn-warning">
                            <span class="glyphicon glyphicon-export"></span> 导出
                        </a>
                        <a href="javascript:;" class="btn btn-warning btn_import">
                            <span class="glyphicon glyphicon-import"></span> 导入
                        </a>
                        <script>
                            $(function () {
                                $('#allCheck').click(function () {
                                    $('.cb').prop('checked',$(this).prop('checked'));
                                })
                                $('.cb').click(function () {
                                    $('#allCheck').prop('checked',$('.cb:checked').length == $('.cb').length)
                                })

                                //弹出模态框
                                $('.btn_import').click(function () {
                                    $('#editModal').modal('show');
                                })

                            })

                            function batchRemove() {
                                if ($('.cb:checked').length == 0){
                                    $.messager.alert('温馨提示','请选择需要删除的数据');
                                    return;
                                }


                                //拿到所有选中的数据
                                var arr = $('.cb:checked');
                                //拿到每条数据的id存放进数组中
                                var ids = $.map(arr,function (ele) {
                                    //console.log(ele);
                                    return $(ele).data('id');
                                })
                                console.log(ids);
                                //设置参数名称不带[]
                                $.ajaxSettings.traditional = true;
                                $.messager.model = {
                                    ok: {text: "确定"},
                                    cancel: {text: "取消"}
                                };
                                $.messager.confirm('温馨提示', '确认是否删除？', function () {
                                    //发送请求删除数据
                                    $.post('/emp/batchRemove.do',{ids:ids},message);
                                })

                            }
                        </script>
                    </form>
                </div>
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="allCheck"></th>
                        <th>编号</th>
                        <th>名称</th>
                        <th>邮箱</th>
                        <th>年龄</th>
                        <th>部门</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <#--判断result是否存在-->
                    <#if result??>
                        <#list result.list as entity>
                        <tr>
                            <td><input type="checkbox" class="cb" data-id="${entity.id}"></td>
                            <td>${entity_index+1}</td>
                            <td>${entity.name!}</td>
                            <td>${entity.email!}</td>
                            <td>${entity.age!}</td>
                            <td>${(entity.dept.name)!}</td>
                            <td>
                            <@shiro.hasPermission name="employee:input">
                                <a href="/emp/input.do?id=${entity.id}" class="btn btn-info btn-xs btn_redirect">
                                    <span class="glyphicon glyphicon-pencil"></span> 编辑
                                </a>
                            </@shiro.hasPermission>
                            <@shiro.hasPermission name="employee:remove">
                                <a href="javascript:remove('${entity.id}')" class="btn btn-danger btn-xs btn_delete">
                                    <span class="glyphicon glyphicon-trash"></span> 删除
                                </a>
                            </@shiro.hasPermission>
                            </td>
                        </tr>
                        </#list>
                        <script>
                            //删除
                            function remove(id) {
                                $.messager.model = {
                                    ok: {text: "确定"},
                                    cancel: {text: "取消"}
                                };
                                $.messager.confirm('温馨提示', '确认是否删除？', function () {
                                    $.get('/emp/remove.do?id=' + id, function (data) {
                                        //调用提示函数
                                        message(data);
                                    })
                                })
                            }
                        </script>
                    </#if>
                </table>
                <!--分页-->
                <#include "../common/page.ftl" />
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl" />
</div>
</body>
</html>
