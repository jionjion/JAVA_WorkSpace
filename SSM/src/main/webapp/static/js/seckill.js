/**
 * 	秒杀逻辑
 */

var seckill = {
		//项目根路径
		//js获取项目根路径，如： http://localhost:8080/ssm
		basePath : function (){
		    //获取当前网址，如： http://localhost:8080/ssm/share/meun.jsp
		    var curWwwPath=window.document.location.href;
		    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		    var pathName=window.document.location.pathname;
		    var pos=curWwwPath.indexOf(pathName);
		    //获取主机地址，如： http://localhost:8083
		    var localhostPaht=curWwwPath.substring(0,pos);
		    //获取带"/"的项目名，如：/uimcardprj
		    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		    return(localhostPaht+projectName);
		},
		
		//封装URL
		URL : {
			//请求当前服务器时钟
			now : function(){
				var basePath = seckill.basePath();
				return  basePath + '/seckill/time/now';	
			},
			//请求接口暴露参数
			exposer : function(seckillId){
				var basePath = seckill.basePath();
				return basePath + '/seckill/' + seckillId + '/exposer'; 
			},
			//秒杀接口
			execution : function(seckillId,md5){
				var basePath = seckill.basePath();
				return basePath + '/seckill/' + seckillId + '/' + md5 + '/execution';
			}
		},
		//执行秒杀逻辑,传入秒杀物品的ID和当前显示的DOM元素
		executeSeckill : function(seckillId,node){
			//首先在操作前隐藏
			node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')
			//时间结束后,执行秒杀逻辑
			$.get(seckill.URL.exposer(seckillId),function(result){
				//判断,获取秒杀暴露对象
				if(result && result['success']){
					//获取秒杀暴露对象
					var exposer = result['data'];
					if(exposer['exposed']){
						//开启秒杀
						var md5 = exposer['md5'];
						var killUrl = seckill.URL.execution(seckillId, md5);
						//点击按钮,执行秒杀
						$('#killBtn').one('click',function(){
							//1.首先禁止按钮
							$(this).addClass('disabled');
							//2.发送秒杀的请求
							$.post(killUrl,function(result){
								//请求成功
								if (result && result['success']) {
									var killResult = result['data'];
									var state = killResult['state'];
									var stateInfo = killResult['stateInfo'];
									//3.显示秒杀结果
									node.html('<span class="label label-success">' + stateInfo + '</span>');
								}
							});
						});
						node.show();
					}else{
						//未开启,可能用户系统时间不一致,返回系统时间进行校准
						var nowTime = exposer['now'];
						var startTime = exposer['start'];
						var endTime = exposer['end'];
						//修正计时
						seckill.countTime(seckillId,nowTime,startTime,endTime);
					}
				}else{
					//失败打印日志
					console.log(result);
				}
			})
		},
		//验证手机号
		validatePhone : function(phone){
			if(phone && phone.length===11 && !isNaN(phone)){
				return true;
			}else{
				return false;
			}
		},
		//计时交互
		countTime : function(seckillId,nowTime,startTime,endTime){
			var seckillBox = $('#seckill-box')
			//时间判断
			if(nowTime > endTime){
				seckillBox.html('秒杀结束!');
			}else if(nowTime < startTime){
				//秒杀未开始,开始计时,将计时组件初始化
				var killTime = new Date(startTime);	//创建日期对象
				//将span元素,绑定countdown插件
				seckillBox.countdown(killTime,function(event){	
					//日期格式化
					var format = event.strftime('秒杀时间: %D天 %H时 %M分 %S秒');
					seckillBox.html(format);
				//组件绑定事件,时间结束
				}).on('finish.countdown',function(){
					seckill.executeSeckill(seckillId,seckillBox);
				});
				
			}else{
				//执行秒杀逻辑
				seckill.executeSeckill(seckillId,seckillBox);
			}
		},
		
		//详情页秒杀逻辑
		detail : {
			//初始化
			init : function(params){
				//手机验证和登录,计时
				//cookie中的手机号
				var userPhone = $.cookie('userPhone');
				//验证手机号,没有绑定
				if(!seckill.validatePhone(userPhone)){
					//绑定手机号
					var userPhoneModal = $('#userPhoneModal');
					userPhoneModal.modal({
						show : true,
						backdrop : false,
						keyboard : false
					});
					$('#userPhoneBtn').click(function(){
						var inputPhone = $('#userPhoneKey').val();
						if(seckill.validatePhone(inputPhone)){
							//写入cookie,注意cookie的写法
							$.cookie('userPhone',inputPhone,{expires:1,path:"/SSM/seckill"});
							//验证通过,刷新页面
							window.location.reload();
						}else{
							$('#userPhoneMessage').hide().html('<label class="label label-danger">手机号码错误!</label>').show(300);
						}
					});
				}
				
				//已经登录
				var seckillId = params['seckillId'];
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				$.get(seckill.URL.now(),function(result){
					if(result){
						//服务器时间
						var nowTime = result['data'];
						//计时交互
						seckill.countTime(seckillId,nowTime,startTime,endTime);
					}else{
						//失败打印日志
						console.log(result);
					}
				});
			}
		}
	}