<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0">

        <title>用户登录</title>
        <meta name="description" content="页面描述" />
        <meta name="keywords" content="关键词" />
        <meta name="author" content="大头希" />

        <!-- Fonts -->  
        <link href="assets/css/oxygen.css" rel="stylesheet" type="text/css">
        <link href="assets/css/source_sans_pro.css" rel="stylesheet" type="text/css">
        <!-- End Fonts -->

        <!--
        <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/font-awesome/4.1.0/css/font-awesome.min.css" />
        <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/ionicons/1.4.1/css/ionicons.min.css" />
        <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/animate.css/3.1.1/animate.min.css" />
        <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css" />
        -->

        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/ionicons.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/animate.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrapValidator.min.css" />

        <link rel="stylesheet" type="text/css" href="assets/css/style.css" />

        <!--
        <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
        <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="http://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
        -->

        
        <script src="assets/script/jquery.min.js"></script>
        <script src="assets/script/bootstrap.min.js"></script>
        <script src="assets/script/bootstrapValidator.min.js"></script>
    </head>
    <body style="padding-top: 50px;">
        #if($msg)
        <h1>${msg}</h1>
        #end
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-default" style="margin-top: 25%;">
                        <div class="panel-heading">
                            <h3 class="panel-title">请登录</h3>
                        </div>
                        <div class="panel-body">
                            <form role="form" method="post" action="${pageContext.request.contextPath}/check/login">
                                <fieldset>
                                    <div class="form-group">
                                        <input name="username" type="text" class="form-control" placeholder="用户名" />
                                    </div>
                                    <div class="form-group">
                                        <input name="userpwd" type="password" class="form-control" placeholder="密码" />
                                    </div>
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" />记住我
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-block btn-lg btn-primary">登 录</button>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    
    <script type="text/javascript">
        $(document).ready(function (){
            $(this).find('form').first().bootstrapValidator({
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields:{
                  username:{
                    message:'The username is not valid',
                    validators:{
                        notEmpty:{
                            message:'用户名不能为空'
                        }
                    }
                  },
                  userpwd:{
                    message:'The password is not valid',
                    validators:{
                        notEmpty:{
                            message:'密码不能为空'
                        }
                    }
                  }
                },
            });
        });
    </script>
</html>
