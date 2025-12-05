<%--
  Created by IntelliJ IDEA.
  User: vic
  Date: 2025/11/18
  Time: 下午6:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gzy.entiy.UserInfo" %>
<%
    // 检查用户是否已经登录
    Object userInfoObj = session.getAttribute("userInfo");
    if (userInfoObj == null) {
        // 用户未登录，重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login-register.html");
        return;
    }
    
    UserInfo userInfo = (UserInfo) userInfoObj;
%>
<!DOCTYPE html>
<html>
<head>
    <title>银行系统 - 存款</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fa;
            color: #333;
        }

        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .logout-btn {
            background: rgba(255,255,255,0.2);
            border: 1px solid rgba(255,255,255,0.3);
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .logout-btn:hover {
            background: rgba(255,255,255,0.3);
        }

        .container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 2rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }

        .card h2 {
            color: #667eea;
            margin-bottom: 1.5rem;
            text-align: center;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
            color: #555;
        }

        .form-input {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
            transition: border-color 0.3s;
        }

        .form-input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .btn {
            display: inline-block;
            padding: 0.8rem 1.5rem;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            transition: transform 0.2s;
        }

        .btn:hover {
            transform: translateY(-2px);
        }

        .btn-block {
            display: block;
            width: 100%;
        }

        .nav-links {
            text-align: center;
            margin-top: 1rem;
        }

        .nav-links a {
            color: #667eea;
            text-decoration: none;
            margin: 0 0.5rem;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        .footer {
            text-align: center;
            padding: 2rem;
            color: #666;
            margin-top: 2rem;
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="logo">
            <i class="fas fa-university"></i> 银行管理系统
        </div>
        <div class="user-info">
            <span>欢迎，<%= userInfo.getNickname() %></span>
            <button class="logout-btn" id="logoutBtn">
                <i class="fas fa-sign-out-alt"></i> 退出
            </button>
        </div>
    </header>

    <div class="container">
        <div class="card">
            <h2><i class="fas fa-arrow-down"></i> 存款</h2>
            <form id="depositForm">
                <div class="form-group">
                    <label class="form-label" for="cardNo">卡号</label>
                    <input type="text" id="cardNo" class="form-input" value="<%= userInfo.getCardNo() %>" readonly>
                </div>
                
                <div class="form-group">
                    <label class="form-label" for="amount">存款金额 (元)</label>
                    <input type="number" id="amount" class="form-input" placeholder="请输入存款金额" step="0.01" min="0.01">
                </div>
                
                <div class="form-group">
                    <label class="form-label" for="password">账户密码</label>
                    <input type="password" id="password" class="form-input" placeholder="请输入账户密码">
                </div>
                
                <button type="submit" class="btn btn-block">
                    <i class="fas fa-check"></i> 确认存款
                </button>
            </form>
            
            <div class="nav-links">
                <a href="user-home.jsp"><i class="fas fa-arrow-left"></i> 返回主页</a>
            </div>
        </div>
    </div>

    <div class="footer">
        <p>&copy; 2025 银行管理系统. 保留所有权利.</p>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // 绑定登出按钮事件
            document.getElementById('logoutBtn').addEventListener('click', handleLogout);
            
            // 绑定存款表单提交事件
            document.getElementById('depositForm').addEventListener('submit', handleDeposit);
        });

        // 处理登出
        async function handleLogout() {
            if (confirm('确定要退出登录吗？')) {
                try {
                    const response = await fetch('/user/logout', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    });
                    
                    // 登出成功，跳转到登录页面
                    window.location.href = '<%= request.getContextPath() %>/login-register.html';
                } catch (error) {
                    console.error('登出错误:', error);
                    alert('网络错误，请稍后重试');
                }
            }
        }
        
        // 处理存款
        async function handleDeposit(e) {
            e.preventDefault();
            
            const amount = document.getElementById('amount').value;
            const password = document.getElementById('password').value;
            
            // 更严格的金额验证
            if (!amount || amount.trim() === '') {
                alert('请输入存款金额');
                return;
            }
            
            // 验证金额格式
            const amountValue = parseFloat(amount);
            if (isNaN(amountValue) || amountValue <= 0) {
                alert('请输入有效的存款金额（必须大于0）');
                return;
            }
            
            // 限制小数点后最多两位
            if (amount.indexOf('.') !== -1 && amount.split('.')[1].length > 2) {
                alert('金额最多保留两位小数');
                return;
            }
            
            if (!password) {
                alert('请输入账户密码');
                return;
            }
            
            // 显示加载状态
            const submitBtn = document.querySelector('#depositForm .btn');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 处理中...';
            submitBtn.disabled = true;
            
            try {
                // 使用 URLSearchParams 替代 FormData 以确保正确的参数传递
                const params = new URLSearchParams();
                params.append('amount', amountValue.toFixed(2));
                params.append('password', password);
                
                const response = await fetch('<%= request.getContextPath() %>/user/deposit', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: params
                });
                
                const result = await response.json();
                
                // 修复：正确处理ResultVO格式的响应
                if (result.success) {
                    // 从data字段获取实际数据
                    const data = result.data || {};
                    let balanceMessage = '存款成功！';
                    if (data.newBalance !== undefined) {
                        balanceMessage = '存款成功！当前余额：¥' + parseFloat(data.newBalance).toFixed(2);
                    }
                    alert(balanceMessage);
                    // 清空表单
                    document.getElementById('amount').value = '';
                    document.getElementById('password').value = '';
                    // 可以考虑刷新用户主页的余额显示
                } else {
                    let errorMessage = result.message || '未知错误';
                    // 提供更友好的错误消息
                    switch(result.message) {
                        case '参数格式错误':
                            errorMessage = '输入的参数格式不正确，请检查金额和密码';
                            break;
                        case '用户密码错误':
                            errorMessage = '密码错误，请重新输入';
                            break;
                        case '系统错误':
                            errorMessage = '系统发生错误，请稍后重试';
                            break;
                    }
                    alert('存款失败：' + errorMessage);
                }
            } catch (error) {
                console.error('存款错误:', error);
                alert('网络错误，请稍后重试');
            } finally {
                // 恢复按钮状态
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            }
        }
    </script>
</body>
</html>