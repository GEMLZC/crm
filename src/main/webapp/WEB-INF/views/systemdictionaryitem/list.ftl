<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>部门管理</title>
    <#include "../common/link.ftl"/>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl"/>
    <!--菜单回显-->
    <#assign currentMenu="systemDictionaryItem"/>
    <#include "../common/menu.ftl"/>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>数据字典明细管理</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-3">
                    <div class="panel panel-info">
                        <div class="panel-heading"><strong>字典明细</strong></div>
                        <ul class="list-group" id="dicDirUl">
                                    <#list dics as d>
                                        <li class="list-group-item"><a class="dicDir" data-pid="${d.id}" href="javascript:">${d.title}</a></li>
                                    </#list>
                            <script>
                                $(".dicDir[data-pid='${qo.parentId!}']").closest("li").addClass("active");
                                $(".dicDir[data-pid='${qo.parentId!}']").css("color","white")
                            </script>
                        </ul>
                    </div>
                </div>
                <script>
                    //字典目录点击
                    $(function () {
                        $('.dicDir').click(function () {
                            var pid = $(this).data('pid');
                            $('#parentId').val(pid);
                            $('#searchForm').submit();
                        })
                    })
                </script>
                <div class="col-md-9">
                    <div class="box">
                        <!--高级查询--->
                        <form class="form-inline" id="searchForm" action="/systemDictionaryItem/list.do" method="post">
                            <input type="hidden" name="currentPage" id="currentPage" value="1">
                            <input type="hidden" name="parentId" id="parentId" value="${qo.parentId!}">
                            <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                                <span class="glyphicon glyphicon-plus"></span> 添加明细
                            </a>
                        </form>
                        <!--编写内容-->
                        <div class="box-body table-responsive no-padding ">
                            <table class="table table-hover table-bordered">
                                <tr>
                                    <th>编号</th>
                                    <th>标题</th>
                                    <th>序号</th>
                                    <th>操作</th>
                                </tr>
                                <!--判断result是否存在-->
                        <#if result??>
                            <#list result.list as entity>
                                <tr>
                                    <td>${entity_index+1!}</td>
                                    <td>${entity.title!}</td>
                                    <td>${entity.sequence!}</td>
                                    <td>
                                        <a href="#" class="btn btn-info btn-xs btn-input" data-json='${entity.json!}'>
                                            <span class="glyphicon glyphicon-pencil"></span> 编辑
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
                                        $.get('/dept/remove.do?id=' + id, function (data) {
                                            //调用提示函数
                                            message(data);
                                        })
                                    })

                                }

                                //新增、编辑
                                $(function () {
                                    $('.btn-input').click(function () {
                                        $('#dictionaryTitle').val($('.list-group-item.active').text());
                                        //每次点击先清除模态框的数据，保证添加数据数据的是空
                                        $('#title').val('');
                                        //$('#dictionaryTitle').val('');
                                        $('#id').val('');
                                        $('#sequence').val('');
                                        //$('#pid').val('');
                                        $('#pid').val(${qo.parentId!});
                                        //拿到自定义属性的值
                                        var json = $(this).data('json');
                                        //给模态框设置值，做编辑回显
                                        $('#title').val(json.title);

                                        $('#id').val(json.id);
                                        $('#sequence').val(json.sequence);

                                    })
                                })


                            </script>
                            <!--分页-->
                    <#include "../common/page.ftl"/>
                        </div>
                    </div>
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
                <h4 class="modal-title" id="myModalLabel">字典明细信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/systemDictionaryItem/save.do" method="post" id="editForm">
                    <input type="hidden" value="" name="id" id="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">字典标题：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="dictionaryTitle" name="dictionaryTitle" value=""
                                   placeholder="请输入标题" readonly>
                            <input type="hidden" value="" name="parentId" id="pid"/>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">明细标题：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="title" name="title" value=""
                                   placeholder="请输入部门编码">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">明细序号：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sequence" name="sequence" value=""
                                   placeholder="请输入简介">
                        </div>
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
            $('#editForm').ajaxSubmit(message)
        })
    })
</script>
</body>
</html>
