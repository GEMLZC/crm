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
    <#assign currentMenu="customer"/>
    <#include "../common/menu.ftl" />
    <div class="content-wrapper">
        <section class="content-header">
            <h1>客户管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <form class="form-inline" id="searchForm" action="/customer/list.do" method="post">
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
</body>
</html>
