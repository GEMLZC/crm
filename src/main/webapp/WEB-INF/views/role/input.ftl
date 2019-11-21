<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>角色管理</title>
    <#include "../common/link.ftl"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl"/>
    <!--菜单回显-->
    <#assign currentMenu="role"/>
    <#include "../common/menu.ftl"/>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>角色编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <script>
                    $(function () {
                        $.validator.addMethod("regex", function(value, element, regexpr) {
                            return regexpr.test(value);
                        }, "编号为字母和数字的组合");   //增加regex正则表达式验证

                        $('#editForm').validate({
                            rules:{
                                name:{
                                    required:true,
                                    minlength:2,
                                    <#if !entity ??>
                                        remote:"/role/checkRole.do"
                                    </#if>
                                },
                                sn:{
                                    required:true,
                                    /*regex: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,*/
                                },
                            },
                            messages: {
                                name: {
                                    required:"必填项",
                                    minlength:"角色名称必须大于等于两个字符",
                                   <#if !entity ??>
                                       remote:"角色已存在"
                                   </#if>
                            },

                            },
                            errorClass:"text-danger"
                        })
                    })
                </script>
                <form class="form-horizontal" action="/role/save.do" method="post" id="editForm">

                    <input type="hidden" value="${(entity.id)!}" name="id">
                    <div class="form-group"  style="margin-top: 10px;">
                        <label for="name" class="col-sm-2 control-label">角色名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name" value="${(entity.name)!}" placeholder="请输入角色名称">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">角色编号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn" value="${(entity.sn)!}" placeholder="请输入角色编号">
                        </div>
                    </div>
                    <div class="form-group " id="role">
                        <label for="role" class="col-sm-2 control-label">分配权限：</label><br/>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-sm-2 col-sm-offset-2">
                                <select multiple class="form-control allPermissions" size="15">
                                </select>
                            </div>
                            <script>
                                $(function () {
                                    $.get('/role/getPermission.do',function (data) {
                                        data.forEach(function (value) {
                                            $('.allPermissions').append("<option value=\""+value.id+"\">"+value.name+"</option>");
                                        })

                                        <#if entity ??>
                                            //回显,只有在编辑的时候才做回显
                                            //右边的选项
                                            var ids = [];
                                            $('.selfPermissions option').each(function () {
                                                ids.push($(this).val());
                                            })
                                            //左边的选项
                                            $('.allPermissions option').each(function () {
                                                if ($.inArray($(this).val(),ids) != -1){
                                                    $(this).remove();
                                                }
                                            })
                                        </#if>

                                    })


                                })
                                function moveSelected(sour , tagle) {

                                    $("."+tagle).append($("."+sour+" option:selected"));
                                }

                                function moveAll(sour , tagle) {
                                    $("."+tagle).append( $("."+sour+" option"));
                                }

                            </script>
                            <div class="col-sm-1" style="margin-top: 60px;" align="center">
                                <div>

                                    <a type="button" class="btn btn-primary" style="margin-top: 10px" title="右移动"
                                       onclick="moveSelected('allPermissions', 'selfPermissions')">
                                        <span class="glyphicon glyphicon-menu-right"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="左移动"
                                       onclick="moveSelected('selfPermissions', 'allPermissions')">
                                        <span class="glyphicon glyphicon-menu-left"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全右移动"
                                       onclick="moveAll('allPermissions', 'selfPermissions')">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全左移动"
                                       onclick="moveAll('selfPermissions', 'allPermissions')">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <select multiple class="form-control selfPermissions" size="15"  name="ids">
                                    <#list (entity.permissions)! as p>
                                        <option value="${p.id}">${p.name}</option>
                                    </#list>
                                </select>
                            </div>
                            <script>
                                $(function () {


                                })

                            </script>

                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="btn_submit" type="button" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>
                    </div>
                    <script>
                        $('#btn_submit').click(function () {
                            $('.selfPermissions option').prop('selected',true);
                            $('#editForm').submit();
                        })
                    </script>
                </form>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl"/>
</div>
</body>
</html>
