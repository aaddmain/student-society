<%--
  Created by IntelliJ IDEA.
  User: vic
  Date: 2025/11/18
  Time: 下午6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gzy.entiy.UserInfo" %>
<%@ page import="com.gzy.entiy.UserAmount" %>
<%@ page import="com.gzy.dao.UserAmountDao" %>
<%
    // 检查用户是否已经登录
    Object userInfoObj = session.getAttribute("userInfo");
    if (userInfoObj == null) {
        // 用户未登录，重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login-register.html");
        return;
    }
    
    // 获取用户信息
    UserInfo userInfo = (UserInfo) userInfoObj;
    
    // 获取用户账户信息
    UserAmountDao userAmountDao = new UserAmountDao();
    UserAmount userAmount = userAmountDao.queryByUserId(userInfo.getId());
    
    // 格式化余额显示（将分转换为元）
    String formattedBalance = "0.00";
    if (userAmount != null && userAmount.getBalance() != null) {
        formattedBalance = String.format("%.2f", userAmount.getBalance() / 100.0);
    }
    
    // 卡类型映射
    String cardTypeText = "";
    if ("1".equals(userInfo.getCardType())) {
        cardTypeText = "储蓄卡";
    } else if ("2".equals(userInfo.getCardType())) {
        cardTypeText = "信用卡";
    } else if ("3".equals(userInfo.getCardType())) {
        cardTypeText = "社保卡";
    }
    
    // 账户状态映射
    String accountStatus = "";
    if (userInfo.getState() == 0) {
        accountStatus = "禁用";
    } else if (userInfo.getState() == 1) {
        accountStatus = "启用";
    } else if (userInfo.getState() == 2) {
        accountStatus = "冻结";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>银行系统 - 用户主页</title>
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
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .welcome-card {
            background: white;
            border-radius: 10px;
            padding: 2rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
            text-align: center;
        }

        .welcome-card h1 {
            color: #667eea;
            margin-bottom: 1rem;
        }

        .user-details {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }

        .user-details h2 {
            color: #667eea;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid #eee;
        }

        .details-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1rem;
        }

        .detail-item {
            margin-bottom: 1rem;
        }

        .detail-label {
            font-weight: bold;
            color: #666;
            margin-bottom: 0.3rem;
        }

        .detail-value {
            font-size: 1.1rem;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .stat-icon {
            font-size: 2.5rem;
            margin-bottom: 1rem;
        }

        .stat-value {
            font-size: 1.8rem;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 0.5rem;
        }

        .stat-label {
            color: #666;
            font-size: 1rem;
        }

        .actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
        }

        .action-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
        }

        .action-icon {
            font-size: 2rem;
            margin-bottom: 1rem;
            color: #667eea;
        }

        .action-title {
            font-weight: bold;
            margin-bottom: 0.5rem;
        }

        .action-desc {
            color: #666;
            font-size: 0.9rem;
        }

        .footer {
            text-align: center;
            padding: 2rem;
            color: #666;
            margin-top: 2rem;
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                gap: 1rem;
                text-align: center;
            }
            
            .stats-grid, .actions-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="logo">
            <i class="fas fa-university"></i> 银行管理系统
        </div>
        <div class="user-info">
            <span id="userName"><%= userInfo.getNickname() %></span>
            <button class="logout-btn" id="logoutBtn">
                <i class="fas fa-sign-out-alt"></i> 退出登录
            </button>
        </div>
    </header>

    <div class="container">
        <div class="welcome-card">
            <h1>欢迎回来，<%= userInfo.getNickname() %>！</h1>
            <p>为您提供安全、便捷的金融服务</p>
        </div>

        <div class="user-details">
            <h2><i class="fas fa-user-circle"></i> 用户信息</h2>
            <div class="details-grid">
                <div class="detail-item">
                    <div class="detail-label">真实姓名</div>
                    <div class="detail-value"><%= userInfo.getName() %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">用户名</div>
                    <div class="detail-value"><%= userInfo.getNickname() %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">手机号</div>
                    <div class="detail-value"><%= userInfo.getMobile() %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">身份证号</div>
                    <div class="detail-value"><%= userInfo.getIdNum() %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">卡类型</div>
                    <div class="detail-value"><%= cardTypeText %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">卡号</div>
                    <div class="detail-value"><%= userInfo.getCardNo() %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">地址</div>
                    <div class="detail-value"><%= userInfo.getAddress() %></div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">账户状态</div>
                    <div class="detail-value"><%= accountStatus %></div>
                </div>
            </div>
        </div>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-wallet"></i>
                </div>
                <div class="stat-value">¥<%= formattedBalance %></div>
                <div class="stat-label">账户余额</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-credit-card"></i>
                </div>
                <div class="stat-value"><%= userInfo.getCardNo().substring(userInfo.getCardNo().length() - 4) %></div>
                <div class="stat-label">尾号</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">
                    <i class="fas fa-user"></i>
                </div>
                <div class="stat-value"><%= accountStatus %></div>
                <div class="stat-label">账户状态</div>
            </div>
        </div>

        <div class="actions-grid">
            <div class="action-card" onclick="handleAction('deposit')">
                <div class="action-icon">
                    <i class="fas fa-arrow-down"></i>
                </div>
                <div class="action-title">存款</div>
                <div class="action-desc">向您的账户存入资金</div>
            </div>
            
            <div class="action-card" onclick="handleAction('withdraw')">
                <div class="action-icon">
                    <i class="fas fa-arrow-up"></i>
                </div>
                <div class="action-title">取款</div>
                <div class="action-desc">从您的账户提取资金</div>
            </div>
            
            <div class="action-card" onclick="handleAction('transfer')">
                <div class="action-icon">
                    <i class="fas fa-exchange-alt"></i>
                </div>
                <div class="action-title">转账</div>
                <div class="action-desc">向其他账户转账</div>
            </div>
            
            <div class="action-card" onclick="handleAction('history')">
                <div class="action-icon">
                    <i class="fas fa-history"></i>
                </div>
                <div class="action-title">交易记录</div>
                <div class="action-desc">查看您的交易历史</div>
            </div>
        </div>
    </div>

    <div class="footer">
        <p>&copy; 2025 银行管理系统. 保留所有权利.</p>
    </div>

    <script>
        // 页面加载完成后初始化
        document.addEventListener('DOMContentLoaded', function() {
            // 绑定登出按钮事件
            document.getElementById('logoutBtn').addEventListener('click', handleLogout);
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
        
        // 处理操作
        function handleAction(action) {
            switch(action) {
                case 'deposit':
                    window.location.href = '<%= request.getContextPath() %>/deposit.jsp';
                    break;
                case 'withdraw':
                    window.location.href = '<%= request.getContextPath() %>/withdraw.jsp';
                    break;
                case 'transfer':
                    alert('转账功能正在开发中...');
                    break;
                case 'history':
                    alert('交易记录功能正在开发中...');
                    break;
                default:
                    alert('功能正在开发中...');
            }
        }
    </script>
</body>
</html>