'use strict';

angular.module('app')
	.controller('obligationGrantForMJSCBLFJController', [ '$rootScope', '$scope', '$http', '$state','toaster',
	                                function($rootScope, $scope, $http, $state,toaster) {
		$scope.title = '我的待办';
        $scope.param = { };
        $scope.loading = false;
		// 保存数据
		$scope.submit= function(){
				$state.go('main.biz.grant.obligationGrantForMJPSJBFH');

		}
		var $wrap = $('#uploader'),

			// 文件容器
			$queue = $( '<ul class="filelist"></ul>' ).appendTo( $wrap.find( '.queueList' ) ),

			// 状态栏，包括进度和控制按钮
			$statusBar = $wrap.find( '.statusBar' ),

			// 文件总体选择信息。
			$info = $statusBar.find( '.info' ),

			// 上传按钮
			$upload = $wrap.find( '.uploadBtn' ),

			// 没选择文件之前的内容。
			$placeHolder = $wrap.find( '.placeholder' ),

			$progress = $statusBar.find( '.progress' ).hide(),

			// 添加的文件数量
			fileCount = 0,

			// 添加的文件总大小
			fileSize = 0,

			// 优化retina, 在retina下这个值是2
			ratio = window.devicePixelRatio || 1,

			// 缩略图大小
			thumbnailWidth = 110 * ratio,
			thumbnailHeight = 110 * ratio,

			// 可能有pedding, ready, uploading, confirm, done.
			state = 'pedding',

			// 所有文件的进度信息，key为file id
			percentages = {},
			// 判断浏览器是否支持图片的base64
			isSupportBase64 = ( function() {
				var data = new Image();
				var support = true;
				data.onload = data.onerror = function() {
					if( this.width != 1 || this.height != 1 ) {
						support = false;
					}
				}
				data.src = "data:image/gif";
				return support;
			} )(),

			// WebUploader实例
			uploader;

		// 实例化
		uploader = WebUploader.create({
			auto: true,
			pick: {
				id: '#filePicker',
				label: '选择文件'
			},
			accept: {
				title: 'Files',
				extensions: 'pdf,txt,doc,docx,gif,jpg,jpeg,bmp,png',//文件后缀
				mimeTypes: 'application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document' +',application/pdf' +',image/!*'
			},
			formData: {
				uid: 123
			},
			dnd: '#dndArea',
			paste: '#uploader',
			swf: 'res/webupload/Uploader.swf',
			server: '/eximbank-club/uploadTest/uploadFile',
			chunked: false,//是否分片上传
			chunkSize: 512 * 1024, //分多大一片
			method:'POST',
			fileNumLimit: 10,                              //最大上传数量为10
			fileSingleSizeLimit: 20 * 1024 * 1024,         //限制上传单个文件大小20M
			fileSizeLimit: 200 * 1024 * 1024,              //限制上传所有文件大小200M
			resize: false
		});
		// 添加“添加文件”的按钮，
		uploader.addButton({
			id: '#filePicker2',
			label: '继续添加'
		});
		uploader.on('ready', function() {
			window.uploader = uploader;
		});
		//上传前的判断处理
		uploader.on('error', function( type ){
			if ( type === 'Q_EXCEED_NUM_LIMIT' ) {
				alert('最多允许上传10份文件');
			}
			if ( type === 'F_DUPLICATE' ) {
				alert('文件重复，不能上传！');
			}else if(type=="F_EXCEED_SIZE"){
				alert("文件大小不能超过20M");
			}
		});



		// 当有文件添加进来时执行，负责view的创建
		function addFile( file ) {
            // $scope.pid = file.id;
            // $scope.panme = file.name;
			var $li = $( '<li id="' + file.id + '">' +
				'<p class="title">' + file.name + '</p>' +
				'<a class="webuploadSeebtn"><p class="imgWrap"></p></a>'+
				'<p class="progress"><span></span></p>' +
				'</li>' ),


				$btns = $('<div class="file-panel">' + '<span class="cancel">删除</span></div>').appendTo( $li ),
				$prgress = $li.find('p.progress span'),
				$wrap = $li.find( 'p.imgWrap' ),
				$info = $('<p class="error"></p>'),

				showError = function( code ) {
					switch( code ) {
						case 'exceed_size':
							text = '文件大小超出';
							break;

						case 'interrupt':
							text = '上传暂停';
							break;

						default:
							text = '上传失败，请重试';
							break;
					}

					$info.text( text ).appendTo( $li );
				};

			if ( file.getStatus() === 'invalid' ) {
				showError( file.statusText );
			} else {
				// @todo lazyload
				$wrap.text( '预览中' );
				uploader.makeThumb( file, function( error, src ) {
					var img;

					if ( error ) {
						$wrap.text( '预览' );
						return;
					}

					if( isSupportBase64 ) {
						img = $('<img src="'+src+'">');
						$wrap.empty().append( img );
					} else {
						$.ajax('res/webupload/preview.php', {
							method: 'POST',
							data: src,
							dataType:'json'
						}).done(function( response ) {
							if (response.result) {
								img = $('<img src="'+response.result+'">');
								$wrap.empty().append( img );
							} else {
								$wrap.text("预览出错");
							}
						});
					}
				}, thumbnailWidth, thumbnailHeight );

				percentages[ file.id ] = [ file.size, 0 ];
				file.rotation = 0;
			}

			file.on('statuschange', function( cur, prev ) {
				if ( prev === 'progress' ) {
					$prgress.hide().width(0);
				} /*else if ( prev === 'queued' ) {
					$li.off( 'mouseenter mouseleave' );
					$btns.remove();
				}*/

				// 成功
				if ( cur === 'error' || cur === 'invalid' ) {
					console.log( file.statusText );
					showError( file.statusText );
					percentages[ file.id ][ 1 ] = 1;
				} else if ( cur === 'interrupt' ) {
					showError( 'interrupt' );
				} else if ( cur === 'queued' ) {
					percentages[ file.id ][ 1 ] = 0;
				} else if ( cur === 'progress' ) {
					$info.remove();
					$prgress.css('display', 'block');
				} else if ( cur === 'complete' ) {
					$li.append( '<span class="success"></span>' );
				}

				$li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
			});

			$li.on( 'mouseenter', function() {
				$btns.stop().animate({height: 30});
			});
            //预览
            $li.on("click", ".webuploadSeebtn", function () {
                var $ele = $(this);
                var id = $ele.parent().attr("id");
                var file = uploader.getFile(id);
                window.open('lib/generic/web/viewer.html?file='+file.name,'PDF','width:50%;height:50%;top:100;left:100;');
            });
			$li.on( 'mouseleave', function() {
				$btns.stop().animate({height: 0});
			});

			$btns.on( 'click', 'span', function() {
				var index = $(this).index(),
					deg;

				switch ( index ) {
					case 0:
						uploader.removeFile( file );
						return;
				}
			});
			$li.appendTo( $queue );
		}

		// 负责view的销毁
		function removeFile( file ) {
			var $li = $('#'+file.id);

			delete percentages[ file.id ];
			updateTotalProgress();
			$li.off().find('.file-panel').off().end().remove();
            $.ajax({
                url: '/eximbank-club/uploadTest/deleteFile',
                type: 'POST',
                data: angular.toJson({
                    'pid':file.id,
                    'pname':file.name
                })
            }).then(function(result) {
                $scope.loading = false;
                if(result.httpCode == 200) {
                    $scope.record = result.data;
                } else {
                    $scope.msg = result.msg;
                }
                $scope.$apply();
            });
		}

		function updateTotalProgress() {
			var loaded = 0,
				total = 0,
				spans = $progress.children(),
				percent;

			$.each( percentages, function( k, v ) {
				total += v[ 0 ];
				loaded += v[ 0 ] * v[ 1 ];
			} );

			percent = total ? loaded / total : 0;


			spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
			spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
			updateStatus();
		}

		//*********************************
		function updateStatus() {
			var text = '', stats;
			if ( state === 'ready' ) {
				text = '选中' + fileCount + '个文件，共' + WebUploader.formatSize( fileSize ) + '。';
			} else if ( state === 'confirm' ) {
				stats = uploader.getStats();
				if ( stats.uploadFailNum ) {
					text = '已成功上传' + stats.successNum+ '文件，'+ stats.uploadFailNum + '文件上传失败'
				}
			} else {
				stats = uploader.getStats();
				text = '共' + fileCount + '个（' + WebUploader.formatSize( fileSize )  + '），已上传' + stats.successNum + '个';
				if ( stats.uploadFailNum ) {
					text += '，失败' + stats.uploadFailNum + '个';
				}
			}
			$info.html( text );
		}

		function setState( val ) {
			var file, stats;

			if ( val === state ) {
				return;
			}

			$upload.removeClass( 'state-' + state );
			$upload.addClass( 'state-' + val );
			state = val;

			switch ( state ) {
				case 'pedding':
					$placeHolder.removeClass( 'element-invisible' );
					$queue.hide();
					$statusBar.addClass( 'element-invisible' );
					uploader.refresh();
					break;

				case 'ready':
					$placeHolder.addClass( 'element-invisible' );
					$( '#filePicker2' ).removeClass( 'element-invisible');
					$queue.show();
					$statusBar.removeClass('element-invisible');
					uploader.refresh();
					break;

				case 'uploading':
					$( '#filePicker2' ).addClass( 'element-invisible' );
					$progress.show();
					//$upload.text( '暂停上传' );
					break;

				case 'paused':
					$progress.show();
					//$upload.text( '继续上传' );
					break;

				case 'confirm':
					$progress.hide();
					$( '#filePicker2' ).removeClass( 'element-invisible' );
					//$upload.text( '开始上传' );
					stats = uploader.getStats();
					if ( stats.successNum && !stats.uploadFailNum ) {
						setState( 'finish' );
						return;
					}
					break;
				case 'finish':
					stats = uploader.getStats();
					break;
			}

			updateStatus();
		}

		uploader.onUploadProgress = function( file, percentage ) {
			var $li = $('#'+file.id),
				$percent = $li.find('.progress span');

			$percent.css( 'width', percentage * 100 + '%' );
			percentages[ file.id ][ 1 ] = percentage;
			updateTotalProgress();
		};

		uploader.onFileQueued = function( file ) {
			fileCount++;
			fileSize += file.size;

			if ( fileCount === 1 ) {
				$placeHolder.addClass( 'element-invisible' );
				$statusBar.show();
			}

			addFile( file );
			setState( 'ready' );
			updateTotalProgress();
		};

		uploader.onFileDequeued = function( file ) {
			fileCount--;
			fileSize -= file.size;

			if ( !fileCount ) {
				setState( 'pedding' );
			}

			removeFile( file );
			updateTotalProgress();

		};

		uploader.on( 'all', function( type ) {
			var stats;
			switch( type ) {
				case 'uploadFinished':
					setState( 'confirm' );
					break;

				case 'startUpload':
					setState( 'uploading' );
					break;

				case 'stopUpload':
					setState( 'paused' );
					break;

			}
		});
		$upload.on('click', function() {
			if ( $(this).hasClass( 'disabled' ) ) {
				return false;
			}

			if ( state === 'ready' ) {
				uploader.upload();
			} else if ( state === 'paused' ) {
				uploader.upload();
			} else if ( state === 'uploading' ) {
				uploader.stop();
			}
		});

		$info.on( 'click', '.retry', function() {
			uploader.retry();
		} );

		$info.on( 'click', '.ignore', function() {
			alert( 'todo' );
		} );

		$upload.addClass( 'state-' + state );
		updateTotalProgress();



	//	$scope.uploadFile();

} ]);