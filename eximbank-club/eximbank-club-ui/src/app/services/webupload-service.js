(function() {

	var WebUploadService = (function() {
		function WebUploadService($q) {
			this.$q = $q;
		}

		WebUploadService.prototype.getCategories = function(params) {
			return null;
		};
		WebUploadService.prototype.initFiles = function(bizCode, bizType, product, fieldName, $scope, $timeout, flag) {
			var uploadSuccess = false;
			uploader = new Array(); //创建 uploader数组
			// 优化retina, 在retina下这个值是2
			var ratio = window.devicePixelRatio || 1,
				// 缩略图大小
				thumbnailWidth = 100 * ratio,
				thumbnailHeight = 100 * ratio,
				supportTransition = (function() {
					var s = document.createElement('p').style,
						r = 'transition' in s ||
						'WebkitTransition' in s ||
						'MozTransition' in s ||
						'msTransition' in s ||
						'OTransition' in s;
					s = null;
					return r;
				})();
			// 所有文件的进度信息，key为file id
			var percentages = {};
			var state = 'pedding';
			var $progress = null;

			if(!WebUploader.Uploader.support()) {
				console.log('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
				throw new Error('WebUploader does not support the browser you are using.');
			}
			
			/**
			 * 功能：清空ul[fileList]
			 * @param {Object} k
			 */
			function removeAllFile(queueList){
				queueList.find('.filelist').each(function(i){
					$(this).remove();
				});
			}
			//循环页面中每个上传域
			$('.uploder-container').each(function(index) {
				var item = $(this);
				// 添加的文件数量
				var fileCount = 0;
				// 添加的文件总大小
				var fileSize = 0;

				var filePicker = $(this).find('.filePicker'); //上传按钮实例
				var queueList = $(this).find('.queueList'); //拖拽容器实例
				var jxfilePicker = $(this).find('.jxfilePicker'); //继续添加按钮实例
				var placeholder = $(this).find('.placeholder'); //按钮与虚线框实例
				var statusBar = $(this).find('.statusBar'); //再次添加按钮容器实例
				var info = statusBar.find('.info'); //提示信息容器实例

				removeAllFile(queueList);
				// 图片容器
				var queue = $('<ul class="filelist"></ul>').appendTo(queueList);

				var urlThumb = "";

				//初始化上传实例
				uploader[index] = WebUploader.create({
					pick: {
						id: filePicker,
						label: '上传'
					},
					//					dnd: queueList,

					//这里可以根据 index 或者其他，使用变量形式
					//					accept: {
					//						title: 'Images',
					//						extensions: 'pdf,xls,xlsx,doc,docx,jpg,png,zip,txt', //文件后缀
					//						mimeTypes: '.pdf,.xls,.xlsx,.doc,.docx,.jpg,.png,.html,.zip,.txt'
					//					},

					// swf文件路径
					swf: 'res/webupload/Uploader.swf',

					disableGlobalDnd: true, //禁用浏览器的拖拽功能，否则图片会被浏览器打开

					chunked: false, //是否分片处理大文件的上传

					server: '/eximbank-club/upload/uploadFile', //上传地址
					method: 'POST',

					fileNumLimit: 1000, //最大上传数量为10
					fileSingleSizeLimit: 10 * 1024 * 1024, //限制上传单个文件大小10M
					fileSizeLimit: 200 * 1024 * 1024, //限制上传所有文件大小200M
					
					duplicate :false, //不允许重复上传
					
					auto: true,
					
					formData: {
						//token:index,//可以在这里附加控件编号，从而区分是哪个控件上传的
						bizCode: bizCode[index],
						bizType: bizType[index],
						product: product[index],
						fieldName: fieldName[index],
						url_: urlThumb
					}
				});
				
				uploader[index].on('error', function (type) {
			        if (type === 'F_DUPLICATE') {
			            alert('提醒','文件重复，不能上传!');
			        } else if (type == "F_EXCEED_SIZE") {
			            alert('提醒',"文件大小不能超过10M!");
			        } else if(type=='Q_EXCEED_SIZE_LIMIT'){
			        	alert('提醒',"限制上传所有文件总大小不能超过200M!");
			        } else if(type=='Q_EXCEED_NUM_LIMIT'){
			        	alert('提醒',"最大上传数量为10个文件!");
			        }
			    });
				
				// 添加“添加文件”的按钮
				if(!flag) {
					uploader[index].addButton({
						id: jxfilePicker,
						label: '继续添加'
					});
				}

				//当文件加入队列时触发	uploader[0].upload();
				uploader[index].onFileQueued = function(file) {
					uploadSuccess = false;
					console.log("add");
					fileCount++;
					fileSize += file.size;

					if(fileCount === 1) {
						placeholder.addClass('element-invisible');
						statusBar.show();
					}

					addFile(file, uploader[index], queue, index, $scope);
					setState('ready', uploader[index], placeholder, queue, statusBar, jxfilePicker);
					updateStatus('ready', info, fileCount, fileSize);
				};

				//当文件被移除队列后触发。
				uploader[index].onFileDequeued = function(file) {
					if(uploadSuccess) {
						fileCount--;
						fileSize -= file.size;

						if(!fileCount) {
							setState('pedding', uploader[index], placeholder, queue, statusBar, jxfilePicker);
							updateStatus('pedding', info, fileCount, fileSize);
							if(index == 0) {
								$scope.realFileName = "";
							} else if(index == 1){
								$scope.nclosureName = "";
							}else{
								$scope.meetFileName = "";
							}
							fileSize = 0;
						} else {
							setState('ready', uploader[index], placeholder, queue, statusBar, jxfilePicker);
							updateStatus('ready', info, fileCount, fileSize);
						}
						removeFile(file, index);
					}
				};

				uploader[index].on('uploadSuccess', function(file, reponse) {
					uploadSuccess = true;
					console.log("上传成功");
					if(index == 1){
						$timeout(function(){
							angular.element('#nclosureName').trigger('blur');
						},520);
					}
				});

				//可以在这里附加额外上传数据

				uploader[index].on('uploadBeforeSend', function(object, data, header) {
					// console.log("上传前触发");
				});
				
				//上传文件进度条...
				uploader[index].on('uploadProgress',function(file, percentage) {
			        var $li = $('#' + file.id),
			            $percent = $li.find('.progress span');
			
			        $percent.css('width', percentage * 100 + '%');
			        //如果存在的条件下才能执行
			        if(percentages[file.id]){
			        	percentages[file.id][1] = percentage;
			        }
			        updateTotalProgress();
			    });
			    /**
				 * 更新进度条
				 */
				function updateTotalProgress() {
			        var loaded = 0,
			            total = 0,
			            spans = $progress.children(),
			            percent;
			
			        $.each(percentages, function (k, v) {
			            total += v[0];
			            loaded += v[0] * v[1];
			        });
			        percent = total ? loaded / total : 0;
			        
			        spans.eq(0).text(Math.round(percent * 100) + '%');
			        spans.eq(1).css('width', Math.round(percent * 100) + '%');
			    }

				//加载的时候
				uploader[index].on('ready', function() {
					uploadSuccess = true;
					$.ajax({
						url: '/eximbank-club/upload/getFileList',
						type: 'POST',
						async: false,
						data: angular.toJson({
							bizCode: bizCode[index],
							bizType: bizType[index],
							product: product[index],
							fieldName: fieldName[index]
						}),
						success: function(files) {
							$scope.realFileName = "";
							$scope.nclosureName = "";
							$scope.meetFileName = "";
							var f_len = files.length;
							if(f_len<1){
								if(index==0){
									cleanFile(item);
								}
								//如果没有文件重新显示大小
								updateStatus('ready', info, 0, 0);
							}else{
								deleteFileDiv(item);
							}
							for(var i = 0; i < f_len; i++) {
								var obj = {};
								statusMap = {};
								fileCount++;
								fileSize += files[i].size_;
								if(fileCount === 1) {
									placeholder.addClass('element-invisible');
									statusBar.show();
								}
								obj.id = files[i].id;
								obj.fdname = files[i].fieldName; //Meetingupload
								obj.name = files[i].realName; //文件名
								obj.name = decodeURI(obj.name); //反编译
								//								$scope.realFileName += obj.name+',';
								obj.time = files[i].createTime;
								obj.type = files[i].fileType;
								obj.size = files[i].size_;
								obj.url_ = files[i].url;
								obj.ext = files[i].ext;
								obj.source = this;
								obj.flog = true;
								obj.status = 'complete',
									obj.getStatus = function() {
										return '';
									}
								obj.version = WebUploader.Base.version;
								obj.statusText = '';
								obj.setStatus = function() {
									var prevStatus = statusMap[this.id];
									typeof text !== 'undefined' && (this.statusText = text);
									if(status !== prevStatus) {
										statusMap[this.id] = status;
										//文件状态设置为已完成
										uploader[index].trigger('statuschage', status, prevStatus);
									}
								}
								addFile(obj, uploader[index], queue, index, $scope);
								setState('ready', uploader[index], placeholder, queue, statusBar, jxfilePicker);
								updateStatus('ready', info, fileCount, fileSize);
							}
						}
					});
				});

				//超时2秒后执行
				setTimeout(function() {
					//追加文件控件的宽，高处理
					$(".webuploader-pick").next().width('100%');
					$(".webuploader-pick").next().height(36);
				}, 2000);
			});
			
			/**
			 * 功能 ：清除文件名称和标题 
			 */
			function cleanFile(cDiv){
				cDiv.find(".filelist").children().each(function(){
					$(this).find(".title").text("");
					$(this).find(".imgWrap").removeAttr("style");
					$(this).find(".title").parent().hide();
				});
			}
			/**
			 * 功能：清除所有子内容
			 * @param {Object} cDiv
			 */
			function deleteFileDiv(cDiv){
				cDiv.find(".filelist").children().each(function(){
					$(this).remove();
				});
			}
			/**
			 * 功能 ：判断文件名称是否重复
			 * @param {Object} fName
			 */
			function isExitsFile(fName){
				var result = false;
				$(".filelist").children().each(function(){
					var li = $(this);
					var a_title = li.find("a").attr("title");
					if(fName==a_title){
						result = true;
					}
				});
				return result;
			}

			// 当有文件添加进来时执行，负责view的创建
			function addFile(file, now_uploader, queue, index, $scope) {
				var fieldName = file.fdname;
				var time = file.time;
				
				
				var fileName = file.name;
				var exits = isExitsFile(fileName);
				//如果文件重复，则不显示
				if(exits){
					return false;
				}
				var filetype = file.type;
				$timeout(function() {
					if (fieldName == "Meetingupload") {
						$scope.issueDate = time;
					}
					if(index == 0) {
						$scope.realFileName += fileName + "，";
					} else if(index == 1){
						$scope.nclosureName += fileName + "，";
					}else{
						$scope.meetFileName += fileName + "，";
					}
				}, 500);
				var $li = $('<li id="' + file.id + '">' +
					'<p class="title">' + file.name + '</p>' +
					'<a title="' + file.name + '" class="webuploadSeebtn">' +
					'<p class="imgWrap"></p></a>' + //lib/generic/web/abc/
					'<p class="progress"><span></span></p>' +
					'</li>');

				var $btns = $('<div class="file-panel">' +
					'<span class="cancel">删除</span>' +
					/*'<span class="rotateRight">向右旋转</span>' +
					'<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),*/
					'</div>').appendTo($li);
//				var $progress = $li.find('p.progress span');
				
				var $wrap = $li.find('p.imgWrap');
				var $info = $('<p class="error"></p>');
		
				// $wrap.text( '缩略展示' );
				//上传过程中如果有错误，那么提示错误消息
				showError = function (code) {
	                switch (code) {
	                    case 'exceed_size':
	                        text = '文件大小超出';
	                        break;
	                    default:
	                        text = '上传失败，请重试';
	                        break;
	                }
	
	                $info.text(text).appendTo($li);
	            };
				$progress = $li.find('p.progress span');

				if(file.flog == true) {
					if(file.type.indexOf("image") != -1) {
						var img = $('<img src="' + file.url_ + '">');
						$wrap.empty().append(img);
					}
				} else {
					now_uploader.makeThumb(file, function(error, src) {
						if(error) {
							//$wrap.text( '无缩略图' );
							return;
						}
						var img = $('<img src="' + src + '">');
						$wrap.empty().append(img);
					}, thumbnailWidth, thumbnailHeight);
				}

				percentages[file.id] = [file.size, 0];
				file.rotation = 0;

				var endFileName = fileName.substring(fileName.lastIndexOf('.') + 1);
				if(endFileName == "xls" || endFileName == "xlsx") {
					$li.find('p.imgWrap').css("background-image", "url(lib/images/x.png)")
				} else if(endFileName == "doc" || endFileName == "docx") {
					$li.find('p.imgWrap').css("background-image", "url(lib/images/w.png)")
				} else if(endFileName == "pdf") {
					$li.find('p.imgWrap').css("background-image", "url(lib/images/p.png)")
				} else if(endFileName == "txt") {
					$li.find('p.imgWrap').css("background-image", "url(lib/images/t.png)")
				} else if(endFileName == "zip") {
					$li.find('p.imgWrap').css("background-image", "url(lib/images/z.png)")
				} else {
					$li.find('p.imgWrap').css("background-image", "url(lib/images/f.png)")
				}
				if(!$.isPlainObject(file)) {

					file.on('statuschange', function(cur, prev) {
						if(prev === 'progress') {
							$progress.hide().width(0);
						}
						// 成功
						if(cur === 'error' || cur === 'invalid') {
							showError(file.statusText);
							percentages[file.id][1] = 1;
						} else if(cur === 'queued') {
							percentages[file.id][1] = 0;
						} else if(cur === 'progress') {
							$info.remove();
							$progress.css('display', 'block');
						} else if(cur === 'complete') {
							$li.append('<span class="success"></span>');
							//判断是否存在合同附件名称字段，如果存在则赋值
							var ren_file_name = $("#renFileName").val();
							if($("#renFileName") && ren_file_name=='' && index=='999'){
								$("#renFileName").val(file.name);
							}
						}

						$li.removeClass('state-' + prev).addClass('state-' + cur);
					});
				} else {
					//控制权限 $btns.remove();
				}
				if(flag) {
					$btns.remove();
				}

				$li.on('mouseenter', function() {
					$btns.stop().animate({
						height: 30
					});
				});

				$li.on('mouseleave', function() {
					$btns.stop().animate({
						height: 0
					});
				});

				$btns.on('click', 'span', function() {
					var index = $(this).index(),
						deg;

					switch(index) {
						case 0:
							now_uploader.removeFile(file, index);
							return;

						case 1:
							file.rotation += 90;
							break;

						case 2:
							file.rotation -= 90;
							break;
					}

					if(supportTransition) {
						deg = 'rotate(' + file.rotation + 'deg)';
						$wrap.css({
							'-webkit-transform': deg,
							'-mos-transform': deg,
							'-o-transform': deg,
							'transform': deg
						});
					} else {
						$wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');

					}
					console.log("on click");

				});
				//预览
				$li.on("click", ".webuploadSeebtn", function() {
					getPreview(bizCode[index], bizType[index], product[index], fieldName[index], fileName, filetype);
				});
				$li.appendTo($(".filelist").eq(index));
			}

			function getPreview(bizCode, bizType, product, fieldName, fileName, fileType) {
				if(uploadSuccess) {
					//fileName = encodeURI(fileName);
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/upload/getPreview',
						timeout: 5000,
						dataType: "text",
						// async: false,
						data: angular.toJson({
							bizCode: bizCode,
							bizType: bizType,
							product: product,
							fieldName: fieldName,
							realName: fileName,
							fileType: fileType
						}),
						success: function(res) {
							console.log(res);
							if(fileType.indexOf("pdf") != -1) {
								window.open('lib/generic/web/viewer.html?file=abc/' + res.data, 'PDF', 'width:50%;height:50%;top:100;left:100;');
							} else if(fileType.indexOf("image") != -1) {
								imgShow("#outerdiv", "#innerdiv", "#bigimg", res.data);
							} else {
								var $eleForm = $("<form method='get'></form>");
								$eleForm.attr("action", "lib/generic/web/abc/" + res.data);
								$(document.body).append($eleForm);
								$eleForm.submit();
							}
						}
					});
				}
			}

			function imgShow(outerdiv, innerdiv, bigimg, src) {
				//var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
				$(bigimg).attr("src", src); //设置#bigimg元素的src属性

				/*获取当前点击图片的真实大小，并显示弹出层及大图*/
				$("<img/>").attr("src", src).load(function() {
					var windowW = $(window).width(); //获取当前窗口宽度
					var windowH = $(window).height(); //获取当前窗口高度
					var realWidth = this.width; //获取图片真实宽度
					var realHeight = this.height; //获取图片真实高度
					var imgWidth, imgHeight;
					var scale = 0.8; //缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

					if(realHeight > windowH * scale) { //判断图片高度
						imgHeight = windowH * scale; //如大于窗口高度，图片高度进行缩放
						imgWidth = imgHeight / realHeight * realWidth; //等比例缩放宽度
						if(imgWidth > windowW * scale) { //如宽度扔大于窗口宽度
							imgWidth = windowW * scale; //再对宽度进行缩放
						}
					} else if(realWidth > windowW * scale) { //如图片高度合适，判断图片宽度
						imgWidth = windowW * scale; //如大于窗口宽度，图片宽度进行缩放
						imgHeight = imgWidth / realWidth * realHeight; //等比例缩放高度
					} else { //如果图片真实高度和宽度都符合要求，高宽不变
						imgWidth = realWidth;
						imgHeight = realHeight;
					}
					$(bigimg).css("width", imgWidth); //以最终的宽度对图片缩放

					var w = (windowW - imgWidth) / 2; //计算图片与窗口左边距
					var h = (windowH - imgHeight) / 2; //计算图片与窗口上边距
					$(innerdiv).css({
						"top": h,
						"left": w
					}); //设置#innerdiv的top和left属性
					$(outerdiv).css("z-index", 999);
					$(outerdiv).fadeIn("fast"); //淡入显示#outerdiv及.pimg
				});

				$(outerdiv).click(function() { //再次点击淡出消失弹出层
					$(this).fadeOut("fast");
					$("<img/>").attr("src", "");
				});
			}

			// 负责view的销毁
			function removeFile(file, index) {
				if(uploadSuccess) {
					var fileName = file.name;
					//file.name = encodeURI(file.name);
					$.ajax({
						url: '/eximbank-club/upload/deleteFile',
						type: 'POST',
						data: angular.toJson({
							bizCode: bizCode[index],
							bizType: bizType[index],
							product: product[index],
							fieldName: fieldName[index],
							realName: file.name,
							fileType: file.type
						})
					}).then(function(result) {
						if(result.httpCode == 200) {
							if(result.data == true) {
								var $li = $(".uploder-container").eq(index).find("#" + file.id);
								delete percentages[file.id];
								$li.off().find('.file-panel').off().end().remove();
							}
							$timeout(function() {
								if(index == 0) {
									var real_before = $scope.realFileName.split(fileName + '，')[0];
									var real_after = $scope.realFileName.split(fileName + '，')[1];
									if($scope.realFileName) {
										$scope.realFileName = real_before + real_after;
									} else {
										$scope.realFileName = "";
									}
								} else if(index == 1){
									var ncse_before = $scope.nclosureName.split(fileName + '，')[0];
									var ncse_after = $scope.nclosureName.split(fileName + '，')[1];
									if($scope.nclosureName) {
										$scope.nclosureName = ncse_before + ncse_after;
									} else {
										$scope.nclosureName = "";
									}
								} else{
									var meet_before = $scope.meetFileName.split(fileName + '，')[0];
									var meet_after = $scope.meetFileName.split(fileName + '，')[1];
									if($scope.meetFileName) {
										$scope.meetFileName = meet_before + meet_after;
									} else {
										$scope.meetFileName = "";
									}
								}
							}, 500);
						}

					});
				}
			}

			function setState(val, now_uploader, placeHolder, queue, statusBar, jxfilePicker) {

				switch(val) {
					case 'pedding':
						placeHolder.removeClass('element-invisible');
						queue.parent().removeClass('filled');
						queue.hide();
						statusBar.addClass('element-invisible');
						now_uploader.refresh();
						break;

					case 'ready':
						placeHolder.addClass('element-invisible');
						jxfilePicker.removeClass('element-invisible');
						queue.parent().addClass('filled');
						queue.show();
						statusBar.removeClass('element-invisible');
						now_uploader.refresh();
						break;
				}
			}
			/**
			 * 上传文件状态修改
			 * @param {Object} val
			 * @param {Object} info
			 * @param {Object} f_count
			 * @param {Object} f_size
			 */
			function updateStatus(val, info, f_count, f_size) {
				var text = '';

				if(val === 'ready') {
					text = '选中' + f_count + '个文件，共' + WebUploader.formatSize(f_size) + '。';
				}
				console.log(text);
				info.html(text);
			}
		};

		return WebUploadService;
	})();

	angular.module('app')
		.factory('WebUploadService', ['$q', function($q) {
			return new WebUploadService($q);
		}]);

})();