<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
     <#include "../common/navbar.ftl"/>
    <!--菜单回显-->
    <#assign  currentMenu="employee"/>
    <#include "../common/menu.ftl"/>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <script>
                    $(function () {
                       $('#editForm').validate({
                           rules: {
                               name: {
                                   required:true,
                                   minlength:2,
                                   <#if !entit??>
                                       remote:"/emp/checkName.do"
                                   </#if>
                               },
                               password: {
                                   required:true,
                                   minlength:1
                               },
                               repassword: {
                                   required:true,
                                   minlength:1,
                                   equalTo:"#password"
                               },
                               email: {
                                   email:true
                               },
                               age: {
                                   range:[18,50]
                               },
                           },
                           messages: {
                               name: {
                                   required:"必填项",
                                   minlength:"姓名必须大于等于两个字符",
                                   <#if !entity??>
                                       remote:"用户已存在"
                                   </#if>
                               },
                               password: {
                                   required:"必填项",
                                   minlength:"密码必须大于等于一个字符"
                               },
                               repassword: {
                                   required:"必填项",
                                   minlength:"密码必须大于等于一个字符",
                                   equalTo:"请输入相同的密码"
                               },
                               email: {
                                   email:"邮箱格式不正确"
                               },
                               age: {
                                   range:"年龄必须在18到50岁"
                               },
                           },
                           errorClass:"text-danger"
                       })
                    })
                </script>
                <form class="form-horizontal" action="/emp/save.do" method="post" id="editForm">
                    <input type="hidden" value="${(entity.id)!}" name="id" >
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name" value="${(entity.name)!}" placeholder="请输入用户名">
                        </div>
                    </div>
                    <#if entity ??>
                        <#else >
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="repassword" class="col-sm-2 control-label">验证密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="repassword" name="repassword" placeholder="再输入一遍密码">
                            </div>
                        </div>
                    </#if>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">电子邮箱：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="email" name="email" value="${(entity.email)!}" placeholder="请输入邮箱">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-sm-2 control-label">年龄：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="age" name="age" value="${(entity.age)!}" placeholder="请输入年龄">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">部门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="dept" name="dept.id">
                                <#--<#forEach items="${depts}" var="d">
                                    <option value="${d.id}">${d.name}</option>
                                </#forEach>-->
                            </select>

                                <script>

                                        //发送ajax请求获取部门数据
                                        $.get('/emp/getDeptMessage.do',function (data) {
                                            //console.log(data);
                                            data.forEach(function (value) {
                                                $('#dept').append("<option value=\""+value.id+"\">"+value.name+"</option>");
                                            })
                                            <#if entity??>
                                                //部门回显，注意发送的是异步请求，如果回显放在外面会同时执行代码
                                                $("#dept option[value=${entity.dept.id}]").prop("selected", true);
                                            </#if>
                                        })
                                </script>

                        </div>
                    </div>
                    <div class="form-group" id="adminDiv">
                        <label for="admin" class="col-sm-2 control-label">超级管理员：</label>
                        <div class="col-sm-6"style="margin-left: 15px;">
                            <input type="checkbox" id="admin" name="admin" class="checkbox">
                            <#if (entity.admin) ??>
                                <#if entity.admin>
                                    <script>
                                        $("#admin").prop("checked", true);
                                    </script>
                                </#if>
                            </#if>
                            <script>
                                var roleDiv;

                                $('#admin').change(function () {
                                  if ($(this).prop('checked')){
                                      //把删除后的返回原来的对象赋值给全局变量
                                      roleDiv = $('#role').detach();
                                  }else {
                                      $('#adminDiv').after(roleDiv);
                                  }
                                })

                                $(function () {
                                    //对编辑时当前员工是超管时的处理
                                    if ($('#admin').prop('checked')){
                                        roleDiv = $('#role').detach();
                                    }
                                })

                            </script>
                        </div>
                    </div>
                    <div class="form-group " id="role">
                        <label for="role" class="col-sm-2 control-label">分配角色：</label><br/>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-sm-2 col-sm-offset-2">
                                <select multiple class="form-control allRoles" size="15">
                                    <#list role as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                                </select>
                            </div>

                            <div class="col-sm-1" style="margin-top: 60px;" align="center">
                                <div>

                                    <a type="button" class="btn btn-primary  " style="margin-top: 10px" title="右移动"
                                       onclick="moveSelected('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-menu-right"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="左移动"
                                       onclick="moveSelected('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-menu-left"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全右移动"
                                       onclick="moveAll('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全左移动"
                                       onclick="moveAll('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a>
                                </div>
                            </div>

                            <div class="col-sm-2">
                                <select multiple class="form-control selfRoles" size="15" name="ids">
                                    <#list (entity.roles)! as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="submitBtn" type="button" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>
                    </div>

                </form>
                <script>
                    $('#submitBtn').click(function () {
                        //把右边的选项全部选中
                        $('.selfRoles option').prop('selected',true);
                        //提交表单
                        $('#editForm').submit();
                    })

                    function moveSelected(sour,target) {
                        $('.'+target).append($('.'+sour+' option:selected'));

                    }
                    function moveAll(sour,target) {
                        $('.'+target).append($('.'+sour+' option'));
                    }

                    $(function () {
                        var ids =[];
                        //获取右边的选项的id并存入到数组
                        $('.selfRoles option').each(function (i,item) {//与原生js一样第一个参数为索引值
                            ids.push($(item).val());
                        })
                        //获取左边的选项的id
                        $('.allRoles option').each(function (i,item) {//与原生js一样第一个参数为索引值
                            if ($.inArray($(item).val(),ids) != -1){//代表找到的情况
                                $(item).remove();
                            }
                        })


                    })
                </script>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl"/>
</div>
</body>
</html>
