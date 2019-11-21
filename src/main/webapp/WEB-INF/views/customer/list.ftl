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
    <#assign currentMenu="customer_potential"/>
    <#include "../common/menu.ftl" />
    <div class="content-wrapper">
        <section class="content-header">
            <h1>潜在客户管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <form class="form-inline" id="searchForm" action="/customer/potentialList.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <div class="form-group">
                            <label for="keyword">关键字:</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}"
                                   placeholder="请输入姓名/电话">
                        </div>
                        <@shiro.hasAnyRoles name="Market_Manager,ADMIN">
                            <div class="form-group">
                                <label for="seller"> 市场专员:</label>
                                <select class="form-control" id="seller" name="sellerId">
                                    <option value="-1">全部</option>
                                        <#list sellers as s>
                                            <option value="${s.id!}">${s.name!}</option>
                                        </#list>
                                </select>
                                <script>
                                    $("#seller option[value='${qo.sellerId!}']").prop("selected", true);
                                    <#--$("#dept option").val('${qo.deptId}');-->
                                </script>
                            </div>
                        </@shiro.hasAnyRoles>
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
                        <th>名称</th>
                        <th>年龄</th>
                        <th>电话</th>
                        <th>QQ</th>
                        <th>职业</th>
                        <th>来源</th>
                        <th>销售员</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <#--判断result是否存在-->
                    <#if result??>
                        <#list result.list as entity>
                        <tr>
                            <td>${entity_index+1}</td>
                            <td>${entity.name!}</td>
                            <td>${entity.age!}</td>
                            <td>${entity.tel!}</td>
                            <td>${entity.qq!}</td>
                            <td>${(entity.job.title)!}</td>
                            <td>${(entity.source.title)!}</td>
                            <td>${(entity.seller.name)!}</td>
                            <td>${entity.statusName!}</td>
                            <td>
                                <a href="#" class="btn btn-info btn-input btn-xs btn_redirect" data-json='${entity.json!}'>
                                    <span class="glyphicon glyphicon-pencil"></span> 编辑
                                </a>
                                <a class="btn btn-primary btn-xs traceBtn"  data-json='${entity.json!}'>
                                    <span class="glyphicon glyphicon-pencil"></span>跟进
                                </a>
                                <@shiro.hasAnyRoles name="ADMIN,Market_Manager">
                                    <a class="btn btn-warning btn-xs transferBtn"  data-json='${entity.json!}'>
                                       <span class="glyphicon glyphicon-pencil"></span>移交
                                    </a>
                                </@shiro.hasAnyRoles>
                                <a class="btn btn-danger btn-xs statusBtn"  data-json='${entity.json!}'>
                                    <span class="glyphicon glyphicon-pencil"></span>修改状态
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
            $('#editModal').modal('show');
        })

        //跟进模态框
        $('.traceBtn').click(function () {
            $('#traceModal').modal('show');
        })

        //移交模态框
        $('.transferBtn').click(function () {
            $('#transferModal').modal('show');
        })

        //修改状态模态框
        $('.statusBtn').click(function () {
            $('#statusModal').modal('show');
        })


        //提交表单
        $('#editModal #save').click(function () {
            //点击之后会变成异步表单并马上提交
            $('#editForm').ajaxSubmit(message)
        })


        //提交表单
        $('.traceSubmit').click(function () {
            //点击之后会变成异步表单并马上提交
            $('#traceForm').ajaxSubmit(message)
        })

        //提交表单
        $('.transferSubmit').click(function () {
            //点击之后会变成异步表单并马上提交
            $('#transferForm').ajaxSubmit(message)
        })

        //提交表单
        $('.statusSubmit').click(function () {
            //点击之后会变成异步表单并马上提交
            $('#statusForm').ajaxSubmit(message)
        })

        //客户编辑模态框回显
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
        })



        //跟进模态框
        $('.traceBtn').click(function () {
            //每次点击先清除模态框的数据，保证添加数据数据的是空
            //$('#traceModal input').val('');
            //拿到自定义属性的值
            var json = $(this).data('json');
            if (json != null){
                //给模态框设置值，做编辑回显
                $('#traceModal input[name=name]').val(json.name);
                $("#traceModal input[name='customer.id']").val(json.id);

            }
        })

        //移交模态框
        $('.transferBtn').click(function () {
            //每次点击先清除模态框的数据，保证添加数据数据的是空
            //$('#traceModal input').val('');
            //拿到自定义属性的值
            var json = $(this).data('json');
            if (json != null){
                //给模态框设置值，做编辑回显
                $("#transferModal input[name='customer.name']").val(json.name);
                $("#transferModal input[name='customer.id']").val(json.id);
                $("#transferModal input[name='oldSeller.name']").val(json.sellerName);
                $("#transferModal input[name='oldSeller.id']").val(json.sellerId);


            }
        })

        //修改状态模态框
        $('.statusBtn').click(function () {
            //每次点击先清除模态框的数据，保证添加数据数据的是空
            //$('#traceModal input').val('');
            //拿到自定义属性的值
            var json = $(this).data('json');
            if (json != null){
                //给模态框设置值，做编辑回显
                $("#statusModal input[name=id]").val(json.id);
                $("#statusModal input[name=name]").val(json.name);
            }
        })

    })
</script>
<#--客户编辑模态框-->
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title inputTitle">客户编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customer/save.do" method="post" id="editForm">
                    <input type="hidden" value="" name="id">
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="name"
                                   placeholder="请输入客户姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户年龄：</label>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" name="age"
                                   placeholder="请输入客户年龄"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户性别：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="gender" >
                                <option value="1">男</option>
                                <option value="0">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户电话：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="tel"
                                   placeholder="请输入客户电话"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户QQ：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="qq"
                                   placeholder="请输入客户QQ"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户工作：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="job.id" >
                                <#list jobs as j>
                                    <option value="${j.id}">${j.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户来源：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="source.id">
                                <#list sources as s>
                                    <option value="${s.id}">${s.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary inputSubmit" id="save">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
            </div>
        </div>
    </div>
</div>

<#--跟进历史-->
<div class="modal fade" id="traceModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">跟进</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTraceHistory/save.do" method="post" id="traceForm">
                <#--新增,新增跟进历史没有ID,客户应该要有ID,不然就不知道当前是哪个客户的跟进历史-->
                    <input type="hidden" name="customer.id"/>
                    <!--客户状态-->
                    <input type="hidden" name="type" value="0"/>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">客户姓名：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" readonly name="name"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">跟进时间：</label>
                        <div class="col-lg-6 ">
                            <input type="text" class="form-control"  name="traceTime" onfocus="WdatePicker({maxDate:new Date(),readOnly:true})" placeholder="请输入跟进时间">
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">跟进记录：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" name="traceDetails"
                                   placeholder="请输入跟进记录"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">交流方式：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceType.id">
                                <#list ccts as c>
                                    <option value="${c.id}">${c.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">跟进结果：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceResult">
                                <option value="3">优</option>
                                <option value="2">中</option>
                                <option value="1">差</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">备注：</label>
                        <div class="col-lg-6">
                            <textarea type="text" class="form-control" name="remark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary traceSubmit">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<#--移交模态框-->
<div id="transferModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTransfer/save.do" method="post" id="transferForm" style="margin: -3px 118px">
                    <input type="hidden" name="id" id="customerTransferId"/>
                    <div class="form-group" >
                        <label for="name" class="col-sm-4 control-label">客户姓名：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control"  name="customer.name"   readonly >
                            <input type="hidden" class="form-control"  name="customer.id"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">旧营销人员：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control"  name="oldSeller.name" readonly >
                            <input type="hidden" class="form-control"    name="oldSeller.id"  >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">新营销人员：</label>
                        <div class="col-sm-8">
                            <select name="newSeller.id" id="newSeller" class="form-control">
                                <#list sellers as e>
                                    <option value="${e.id}">${e.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">移交原因：</label>
                        <div class="col-sm-8">
                            <textarea type="text" class="form-control" id="reason" name="reason" cols="10" ></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary transferSubmit" >保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<#--修改状态-->
<div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">修改客户状态</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customer/updateStatus.do" method="post" id="statusForm">
                    <input type="hidden" name="id" value=""/>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">客户名称：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" name="name"
                                   readonly placeholder="请输入客户名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">客户状态：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="status">
                                <option value="0">潜在客户</option>
                                <option value="1">移入客户池</option>
                                <option value="2">转正式客户</option>
                                <option value="3">开发失败</option>
                                <option value="4">客户流失</option>
                            </select>
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
