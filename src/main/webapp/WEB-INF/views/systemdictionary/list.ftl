﻿<!DOCTYPE html>
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
    <#assign currentMenu="systemDictionary"/>
    <#include "../common/menu.ftl"/>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>字典目录管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/systemDictionary/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                            <span class="glyphicon glyphicon-plus"></span> 添加
                        </a>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>标题</th>
                            <th>编码</th>
                            <th>简介</th>
                            <th>操作</th>
                        </tr>
                        <!--判断result是否存在-->
                        <#if result??>
                            <#list result.list as entity>
                                <tr>
                                    <td>${entity_index+1!}</td>
                                    <td>${entity.title!}</td>
                                    <td>${entity.sn!}</td>
                                    <td>${entity.intro!}</td>
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
                                //每次点击先清除模态框的数据，保证添加数据数据的是空
                                $('#title').val('');
                                $('#sn').val('');
                                $('#id').val('');
                                $('#intro').val('');
                                //拿到自定义属性的值
                                var json = $(this).data('json');
                                //给模态框设置值，做编辑回显
                                $('#title').val(json.title);
                                $('#sn').val(json.sn);
                                $('#id').val(json.id);
                                $('#intro').val(json.intro);
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
                <h4 class="modal-title" id="myModalLabel">字典目录信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/systemDictionary/save.do" method="post" id="editForm">
                    <input type="hidden" value="" name="id" id="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">标题：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="title" name="title" value=""
                                   placeholder="请输入标题">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">编码：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sn" name="sn" value=""
                                   placeholder="请输入部门编码">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">简介：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="intro" name="intro" value=""
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
            $('#editForm').ajaxSubmit(function (data) {
                //调用提示函数
                message(data);
            })
        })
    })
</script>
</body>
</html>
