var projectId = null;
var projectName = null;
var storyId = null;
var cardName = null;
var storystatus = null;
var muliRoleFlag = 0;
var autoSignetDefault = 0;
var submitThreadhold = 0;
var crmSign = {};
var editFlag = false;
var bugSpaceId = '';

crmSign.tree = (function(){
    var locked = true;
    var masker = null;
    var treeContainner = null;
    var treeData = null;
    var orginTreeData = null;
    var tempData = null;
    var nowAtNodeId = null;
    var containner = null;
    var newNode = null;
    var newNodeData = null;
    var movedDiv = null;
    var moveToLeft = null;
    var moveToTop = null;

    var startPointX = null;
	var startPointY = null;
    var firstClick = null;
    var editTextBox = null;
    var needCopyNodeId = null;
    var testSigns = {
        
        't0':'请选择',
        't9':'前端页面',
        't1':'单元测试',
        't2':'单元系统',
        't3':'单元集成',
        't4':'单集系印',
        't5':'系统测试',
        't6':'自动化印',
        't7':'系统集成',
        't8':'集成系统'
    };

    var nodeTypes = {
        'first' : 'firstNodeTd',
        'center' : 'centerNodeTd',
        'last' : 'lastNodeTd',
        'only' : 'onlyNodeTd'
    };

    var rolesMap = {

    };
    var getQuestions = function(nodeId, callback) {
        request.dao.queryRemark(projectId,storyId,nodeId, function(data) {
            if(data && callback) {
            	callback(data);
            }
        });
    };
    var setQuestionsToPage = function(data) {
        var con = baidu.g('question_message');
        var htmls = [];
        baidu.each(data, function(item) {
            htmls = htmls.concat([
                                    '<div class="message">',
                                        '<div class="message_title">',
                                            '<span class="message_user">',
                                                item['user'],
                                            ':</span>',
                                             '<span nodeId="' + item['nodeId'] + '" messageId="' + item['id'] + '" class="message_delete">',
                                            '</span>',
                                            '<span class="message_date">',
                                                item['addDate'],
                                            '</span>',
                                        '</div>',
                                        '<div class="message_content">',
                                            item['content'],
                                        '</div>',
                                    '</div>'
                                    ]);
        });
        con.innerHTML = htmls.join('');
    };
    var utils = {
        'trimString' : function(source, length, className, nodeId) {
            if(!className) {
                className = "";
            }
            if(baidu.string.getByteLength(source) > length) {
                return '<span class="' + className + '" target="_blank" textId="'+nodeId+'" title="' + source + '">' + baidu.string.subByte(source, length - 1) + '…</span>';
            } else {
                return '<span class="' + className + '" target="_blank" textId="'+nodeId+'"  title="">' + source + '</span>';
            }
        },
        alert : function(title, text, okCallback, cancelCallback) {
            var html = ['<div class="alert_win">',
                            '<div class="alert_title">',
                                title,
                            '</div>',
                            '<div class="alert_content">',
                                text,
                            '</div>',
                            '<div class="alert_buttons">',
                                '<div class="alert_btn" id="btn_ok">',
                                    '确定',
                                '</div>',
                                '<div class="alert_btn" id="btn_cancel" >',
                                    '取消',
                                '</div>',
                            '</div>',
                        '</div>'];
            if(!masker) {
                 masker = baidu.dom.create('div', {
                     'class':'masker',
                     'style':'height:' + (treeContainner.scrollHeight || treeContainner.offsetHeight)+'px'
                 });
            }
            masker.innerHTML = html.join('');
            treeContainner.appendChild(masker);
            baidu.g('btn_ok').onclick = function() {
                treeContainner.removeChild(masker);
                masker = null;
                if(okCallback) {
                    okCallback();
                }
            };
            baidu.g('btn_cancel').onclick = function() {
                treeContainner.removeChild(masker);
                masker = null;
                if(cancelCallback) {
                    cancelCallback();
                }
            };
        },

        showMessageBox : function(nodeId, hasQuestion) {
            var htmls = ['<div class="question_win">',
                            '<div class="question_title">',
                                '验收条件-批注',
                            '</div>',
                            '<div id="question_content" class="question_content">',   
                                '<div id="question_message" >',
                                '</div>',
                                '<div id="question_ask">',
                                    '<textarea id="question_ask_text"></textarea>',
                                '</div>',
                            '</div>',
                            '<div class="question_buttons">',
                                '<div class="question_btn" id="question_btn_reply" style="margin-right:3px">',
                                    '保存',
                                '</div>',
                                '<div class="question_btn" id="question_btn_cancel" >',
                                    '取消',
                                '</div>',
                            '</div>',
                        '</div>'];
            var addMasker = function() {
                if(!masker) {
                    masker = baidu.dom.create('div', {
                        'id' : 'messsageBoxMasker',
                        'class':'masker',
                        'style':'height:' + (treeContainner.scrollHeight || treeContainner.offsetHeight) + 'px'
                    });
                }
                masker.innerHTML = htmls.join('');
                treeContainner.appendChild(masker);

                baidu.on('question_btn_reply', 'click', function(event) {
                    var text = baidu.g('question_ask_text').value;
                    var nodeData = utils.findNodeData(treeData, nodeId);
                    baidu.g('loading').style.display='';
                    request.dao.addRemark(projectId,storyId,nodeId,{                   
                        'content': text
                    }, function() {
                        baidu.g('question_ask_text').value = '';
                        getQuestions(nodeId, setQuestionsToPage);
                        nodeData.remarkFlag = '1';
                        repaintTree();
                        baidu.g('loading').style.display='none';
                    });
                });

                baidu.on('question_btn_cancel', 'click', function(event) {
                    treeContainner.removeChild(masker);
                    masker = null;
                }); 
            };

            addMasker();
            if (hasQuestion) {
                getQuestions(nodeId, setQuestionsToPage);
            }
        },
        
       
        
        showSubmitBox : function() {
            var htmls = ['<div class="question_win">',
                         '<div class="question_title">',
                             '提测',
                         '</div>',
                         '<div id="question_content" class="question_content">',   
                             '<div id="question_ask">',
                                 '<textarea placeholder="邮件正文" style="width:670px;height:40px" id="submit_text"></textarea>',
                             '</div>',
                         '</div>',
                         '<div class="question_buttons">',
                             '<input id="submit_mail" type="text" placeholder="邮件地址分号分割" style="margin-right:20px">',
                             '<div class="question_btn" id="submit_btn" style="margin-right:3px">',
                                 '提测',
                             '</div>',
                             '<div class="question_btn" id="submit_btn_cancel" >',
                                 '取消',
                             '</div>',
                         '</div>',
                     '</div>'];


            var addMasker = function() {
                if(!masker) {
                    masker = baidu.dom.create('div', {
                        'id' : 'submitBoxMasker',
                        'class':'masker',
                        'style':'height:' + (treeContainner.scrollHeight || treeContainner.offsetHeight) + 'px'
                    });
                }
                masker.innerHTML = htmls.join('');
                treeContainner.appendChild(masker);

                baidu.on('submit_btn', 'click', function(event) {
                    var text = baidu.g('submit_text').value;
                	var content='';  
                	baidu.g('loading').style.display='';
                    request.dao.submitStory({
                        'projectId' : projectId,
                        'storyId' : storyId,
                        'storyStatus' : storystatus,
                        'spaceName' : projectName,
                        'chkcontent': content,
                        'content': text
                    }, function(data){
                    	if(!data.changeIcafe){
                    		 crmSign.tree.utils.alert('提示信息','修改icafe卡片状态失败，请确认提交后状态设置是否正确');
                    	}else{
                    		if(!data.mail){
                    			crmSign.tree.utils.alert('提示信息','提测成功，但未发送邮件');
                    		}else{
                    			crmSign.tree.utils.alert('提示信息','提测成功');
                    		}
                    		 baidu.g('submit_text').value = '';
                             baidu.g('submit_mail').value = '';
                             baidu.g('submitBtn').innerHTML="已提测";
                             treeContainner.removeChild(masker);
                             masker = null;
                    	}
                    	baidu.g('loading').style.display='none';
                    }, function(){
                        crmSign.tree.utils.alert('提示信息','提测失败');
                    });
                });

                baidu.on('submit_btn_cancel', 'click', function(event) {
                    treeContainner.removeChild(masker);
                    masker = null;
                }); 
            }

            addMasker();
        },
        showInput : function(target, text, okCallback, cancelCallback) {
            if(baidu.dom.hasClass(target, 'taskText')) {
                target = baidu.dom.getParent(target);
            }
            var pos = baidu.dom.getPosition(target);
            pos.left = pos.left + treeContainner.scrollLeft;
            pos.top = pos.top + treeContainner.scrollTop - baidu.g('cardDetail').offsetHeight;

            if(!editTextBox) {
                 editTextBox = baidu.dom.create('textarea', {
                     'id' : 'changeTextArea',
                     'class' : '',
                     'style' : 'height:' + (target.offsetHeight - 2) + 'px;width:' + (target.offsetWidth - 2) + 'px;position:absolute;z-index:998;left:' + pos.left + 'px;top:' + (pos.top - 58) + 'px'
                     //'style':'height:' + (treeContainner.scrollHeight||treeContainner.offsetHeight)+'px'
                 });
                 if("undefined" != typeof text){
                     editTextBox.innerHTML = text.replace(/<br \/>/g, '\n');
               }else{
            	   editTextBox.innerHTML ='';
             }
            }
            //masker.innerHTML = html.join('');
            treeContainner.appendChild(editTextBox);
            editTextBox.onblur = function() {
                var text = editTextBox.value;
                text = text.replace(/\n/g, '<br />');
                treeContainner.removeChild(editTextBox);
                editTextBox = null;
                if(okCallback) {
                    okCallback(text);
                }
            }
            editTextBox.focus();
        },
        findNodeData : function(data, id) {
            var finedItem = null
            for(var i=0; i<data.length; i++){
                var item = data[i];
                if(item.id == id) {
                    finedItem = item;
                } 
                else if(item.children) {
                    var temp = utils.findNodeData(item.children, id);
                    if(temp){
                       finedItem = temp;
                    }
                }
            }
            return finedItem;
        },
        findNodeContainer : function(data, id){
            var finedItem = null
            for(var i=0; i<data.length; i++){
                var item = data[i];
                if(item.id == id) {
                    finedItem = data;
                } 
                else if(item.children) {
                    var temp = utils.findNodeContainer(item.children, id);
                    if(temp){
                       finedItem = temp;
                    }
                }
            }
            return finedItem;
        },
           isParentNode: function(children, nowAtId) {
            var flag = false;
            for(var i = 0; i < children.length; i++){
                var item = children[i];
                if(item.id == nowAtId) {
                    flag = true;
                } 
                else if(item.children) {
                    if(utils.isParentNode(item.children, nowAtId)) {
                        flag = true;
                    }
                }
            }
            return flag;
        }
    }

    var createSignLayer = function(roles){
        var signHTML = [];
        baidu.each(roles, function(item) {
            var roleImg;
            if(item.roleId == 0){
                roleImg = '<img class="aSignRole" roleId="'+item.roleId+'"  src="../resources/images/signet/choose.png" />';
            }else if(item.roleId == -1){
                roleImg = '<img class="aSignRole" roleId="'+item.roleId+'"  src="../resources/images/signet/noneed.png" />';
            }else if(item.roleId == -2){
                roleImg = '<img class="aSignRole" roleId="'+item.roleId+'"  src="../resources/images/signet/zhunru.png" />';
            }else if(item.roleId == -3){
                roleImg = '<img class="aSignRole" roleId="'+item.roleId+'"  src="../resources/images/signet/needpm.png" />';
            }
            else{
                roleImg = '<img class="aSignRole" roleId="'+item.roleId+'"  src="../resources/signs/' + projectId +'/' + item.roleTag + '.jpg" />';
            }
            
            signHTML = signHTML.concat(
                        ['<div class="aSign" roleId="'+item.roleId+'" roleName="' + item.name + '">',
                            roleImg,
                            '<div class="aSignRoleName" roleId="'+item.roleId+'"  >' + item.name + '</div>',
                        '</div>']);
        });
        if(!baidu.g('signet')) {
            var signetEle = baidu.dom.create('div', {
                'style': 'display:none',
                'id': 'signet'
            });
            containner.appendChild(signetEle);
        };
        baidu.g('signet').innerHTML = signHTML.join('');
    };

    var createTestSignLayer = function(){
        var signHTML = [];
        for(var item in testSigns) {
            var testSignId = item.replace('t','');
            signHTML = signHTML.concat([
                        '<div class="aTestSign" testSignId="' + testSignId + '" >',
                            '<img class="aTestSignImg" testSignId="' + testSignId + '" src="../resources/images/testSign/' + testSignId + '.png" />',
                            '<div class="aTestSignText" testSignId="' + testSignId + '" >' + testSigns[item] + '</div>',
                        '</div>']);
        };
        if(!baidu.g('testSign')) {
            var testSign = baidu.dom.create('div', {
                'style': 'display:none',
                'id': 'testSign'
            });
            containner.appendChild(testSign);
        };
        baidu.g('testSign').innerHTML = signHTML.join('');
    };

    var opEvents = {    

        deleteNode : function(nodeId) {
            utils.alert('提示信息', '您真的要删除此节点吗？', function(){
            	baidu.g('loading').style.display='';
                request.dao.deleteNode(projectId,storyId,nodeId,function(id){
                	if(id==0){
                		utils.alert('提示信息', '其他人正在编辑，请等待编辑完成');
                		baidu.g('loading').style.display='none';
                		return;
                	}
                    var nodeData = utils.findNodeData(treeData, nodeId);
                    if(nodeData){
                        var nodeDataCon = utils.findNodeContainer(treeData, nodeId);
                        baidu.array.remove(nodeDataCon, nodeData);
                        repaintTree();
                    }
                    else if(newNode) {
                        containner.removeChild(newNode);
                        newNodeData = null;
                        newNode = null;
                    }
                    baidu.g('loading').style.display='none';
                });
            });
        },

        changeNodeText : function(nodeId, target) {
            var nodeData = utils.findNodeData(treeData, nodeId) || newNodeData;
            var text = nodeData.text;
            utils.showInput(target, text, function(value) {
            	baidu.g('loading').style.display='';
                request.dao.updateNode(projectId,storyId,nodeId,'text',{
                    'nodeText' : value
                },function(){
                    nodeData.text = value;
                    repaintTree();
                    baidu.g('loading').style.display='none';
                }, function(status, statusObj){
                    defaultError();
                });
            });
        },

        changeRole : function(nodeId, target) {
            var pos = baidu.dom.getPosition(target);

            baidu.g('signet').style.left = pos.left - 11 + treeContainner.scrollLeft + 'px';
            baidu.g('signet').style.top = pos.top -28 + treeContainner.scrollTop-baidu.g('cardDetail').offsetHeight + 'px';
            baidu.dom.show(baidu.g('signet'));
        },

        changeRoleSign : function(nodeId, roleId) {
            var nowData = utils.findNodeData(treeData, nodeId) || newNodeData;
            var tmpRoleId = nowData.roleId.split(",");
            var finalRoleId = '';
            var flag = false;
            if(muliRoleFlag!=1){
            	nowData.roleId = roleId;
            }else{
            	 if(roleId=='0'||roleId=='-1'||roleId=='-2'||roleId=='-3'){
                 	nowData.roleId = roleId;
                 }else{
                 	for(var i=0; i<tmpRoleId.length; i++){
                 		if(tmpRoleId[i] =='0' || tmpRoleId[i]=='-1'|| tmpRoleId[i]=='-2'|| tmpRoleId[i]=='-3'){
                 			continue;
                 		}
                 		
                     	if(tmpRoleId[i]==roleId){
                     		flag = true;
                     	}else{
                     		if(finalRoleId != ''){
                     			finalRoleId = finalRoleId + "," + tmpRoleId[i];
                     		}else{
                     			finalRoleId = tmpRoleId[i];
                     		}
                     		
                     	}
                     }
                     if(!flag){
                     	if(finalRoleId != ''){
                 			finalRoleId = finalRoleId + "," + roleId;
                 		}else{
                 			finalRoleId = roleId;
                 		}
          
                     }
                     nowData.roleId = finalRoleId;
                 }
            }
            baidu.g('loading').style.display='';
            request.dao.updateNode(projectId,storyId,nodeId,'role',{'roleSignId':nowData.roleId}, function(){
                baidu.dom.hide(baidu.g('signet'));
                repaintTree();
                baidu.g('loading').style.display='none';
            }, function(status, statusObj){
                defaultError();
            });
        },
        selectNode : function(nodeId, target) {
            if(baidu.dom.hasClass(target, 'taskText')) {
                target = baidu.dom.getParent(target);
            }
            baidu.each(baidu.dom.query('.nodeText-active'), function(item) {
                baidu.dom.removeClass(item, 'nodeText-active');
            });
            baidu.dom.addClass(target, 'nodeText-active');
        },

        copyNode : function(nodeId, target) {
            var nowData = utils.findNodeData(treeData, nodeId)||newNodeData;
        },

        changeChildrenStatus : function(target, id) {
            var nodeData = utils.findNodeData(treeData, id);
            var childContainner = baidu.dom.next(baidu.dom.getParent(baidu.dom.getParent(target)));
            if(baidu.dom.hasClass(target, 'child_control_handle_minus')) {
                baidu.dom.removeClass(target, 'child_control_handle_minus');
                baidu.dom.addClass(target, 'child_control_handle_plus');
                baidu.dom.hide(childContainner);
                nodeData.control = true;
            }
            else {
                baidu.dom.removeClass(target, 'child_control_handle_plus');
                baidu.dom.addClass(target, 'child_control_handle_minus');
                baidu.dom.show(childContainner);
                nodeData.control = false;
            }

        },
        
        changeRetestStatus : function(target, id) {
        	var nodeData = utils.findNodeData(treeData, id) || newNodeData;
        	var containner = baidu.dom.getParent(target);
       	 	if(nodeData.isRetest){
       	 		var isRetest = 1;
       	 	}else{
       	 		var isRetest = 0;
       	 	}
       	 	baidu.g('loading').style.display='';
        	request.dao.updateNode(projectId,storyId,id,'retest',{
                'isRetest':isRetest
            }, function(){
            	
			if(isRetest==1) {
           	 		baidu.dom.removeClass(containner, 'nodeText-isretest');
				nodeData.isRetest=0;
           	 	}else{
           	 		baidu.dom.addClass(containner, 'nodeText-isretest');
				nodeData.isRetest=1;
           	 	}
				repaintTree();
				baidu.g('loading').style.display='none';
            }, function(status, statusObj){
                defaultError();
            });
        },

        clearSelectNode : function() {
            baidu.each(baidu.dom.query('.nodeText-active'), function(item) {
                baidu.dom.removeClass(item, 'nodeText-active');
            });
        },

        viewMessage : function(nodeId, hasQuestion) {
            utils.showMessageBox(nodeId, hasQuestion);
        },
        
        removeRemark : function(nodeId, messageId) {
        	baidu.g('loading').style.display='';
            request.dao.deleteMessage(projectId, storyId, nodeId, storyId, messageId, function() {
                getQuestions(nodeId, setQuestionsToPage);
                baidu.g('loading').style.display='none';
            })
        }
    }
    
    var node = function(nodeId, roleId, testSignId, text, nodeType, hasChild, isObj, control, isQuestion, isAppraise,isRetest) {
        var moveHandle = '<div class="move_handle" moveId="' +nodeId+ '"></div>';
       
        var srcStr = [];
        if(roleId=='choose'){
        	roleId='0';
        }
        if(roleId==0){
        	roleId='0';
        }else if(roleId==-1){
        	roleId='-1';
        }else if(roleId==-2){
        	roleId='-2';
        }else if(roleId==-3){
        	roleId='-3';
        }
        var ids = roleId.split(",");
       
        if(roleId == '0' || roleId == 'choose'){
             srcStr[0] = '../resources/images/signet/choose.png';
        }else if(roleId=='-1' || roleId=='noneed'){
             srcStr[0] = '../resources/images/signet/noneed.png';
        }else if(roleId=='-2' || roleId=='zhunru'){
             srcStr[0] = '../resources/images/signet/zhunru.png';
        }else if(roleId=='-3' || roleId=='needpm'){
             srcStr[0] = '../resources/images/signet/needpm.png';
        }else {
        	 for(var i=0; i<ids.length; i++){
        		 if(ids[i] == '0' || ids[i] == 'choose'){
                     srcStr[i] = '../resources/images/signet/choose.png';
                }else if(ids[i]=='-1' || ids[i]=='noneed'){
                     srcStr[i] = '../resources/images/signet/noneed.png';
                }else if(ids[i]=='-2' || ids[i]=='zhunru'){
                     srcStr[i] = '../resources/images/signet/zhunru.png';
                }else if(ids[i]=='-3' || ids[i]=='needpm'){
                     srcStr[i] = '../resources/images/signet/needpm.png';
                }else{
                	 srcStr[i] = '../resources/signs/' +projectId+'/'+ rolesMap[ids[i]] + '.jpg';
                }
        		
        	 }
        }
        var nodeClass = hasChild ? 'parentNode' : 'childNode';
        var signet = ''; 
        for(var i=0; i<ids.length; i++){
        	if(i==0){
        		signet = signet + '<img class="role" nodeId="'+nodeId+'" roleId="'+ ids[i] +'" src="'+srcStr[i]+'" />';

        	}else{
        		signet = signet + '<img class="role tmp" nodeId="'+nodeId+'" roleId="'+ ids[i] +'" src="'+srcStr[i]+'" />';

        	}
        	
   	 	}
        
        var showText = utils.trimString((text || ''), 500, 'taskText', nodeId);
        var deleteHandel = '<div class="delete_handle" deleteId="' + nodeId + '" title="删除此节点？"></div>';
        var childHandelClass = control ? 'child_control_handle_plus' : 'child_control_handle_minus' ;
        var childHandel = '<div controlId="' + nodeId + '" class="child_control_handle ' + childHandelClass + '"></div>';
        var isQuestion = isQuestion ? 'questionMark' : '';
        var questionTitle = isQuestion ? '查看质疑' : '点击质疑';
        var marker = '<span  nodeId="' + nodeId + '"  title="' + questionTitle + '" class="marker ' + isQuestion + '"></span>';
        var retestTitle = isRetest==0? '回归标记' : '回归标记';
        var retestHandle = '<div class="retest_handle" title="'+ retestTitle +'"retestId="' +nodeId+ '"></div>';
        signet = hasChild ? '' : signet;
        childHandel = hasChild ? childHandel : '';
        var htmls = [
                        moveHandle,
                        signet,
                        showText,
                        deleteHandel,
                        retestHandle,
                        childHandel,
                        marker
                        // marker //暂未打开
                    ];
        var obj ;
        if(isObj){
	    if(isRetest == 1){
            obj = baidu.dom.create('div', {
                'class':'nodeText ' + nodeClass+' nodeText-isretest',
                'textId' : nodeId
            });
		}else{

 obj = baidu.dom.create('div', {
                'class':'nodeText ' + nodeClass,
                'textId' : nodeId
            });
}
            obj.innerHTML = htmls.join('');
        }else {
        	if(isRetest == 1){
        		obj =  '<div class="nodeText ' + nodeClass + ' nodeText-isretest" textId="' + nodeId + '">' +  htmls.join('') + '</div>';
        	}else{
        		obj =  '<div class="nodeText ' + nodeClass + '" textId="' + nodeId + '">' +  htmls.join('') + '</div>';
        		
        	}
            
        }
        return obj;
    }

    var getNodeType = function(nodes, order) { 
        var nodeType = null;
        if (nodes.length == 1 && order == (nodes.length - 1)) {
            nodeType = 'only';
        } 
        else if (order == 0 && nodes.length > 1) {
            nodeType = 'first';
        } 
        else if (nodes.length > 1 && order == (nodes.length - 1)) {
            nodeType = 'last';
        } 
        else {
            nodeType = 'center';
        }
        return nodeTypes[nodeType];
    }

    var createTree = function(treeData, flag) {
        var html = [];
        baidu.each(treeData, function(treeNode) {
            treeNode.children = treeNode.children || [];

            var order = baidu.array.indexOf(treeData, treeNode);
            var nodeType = getNodeType(treeData, order);
            
            var isRetest = treeNode.isRetest;
            var nodeId = treeNode.id;
            var roleId = treeNode.roleId;
            var testSignId = treeNode.testSignId;
            var hasChild = !!treeNode.children.length;
            var nodeCss = hasChild ? 'parentNodeTd' : 'childNodeTd';
            var text = treeNode.text|| '';
            treeNode.control = treeNode.control ? true : false; 
            var nodeDomId = flag ? ' id="rootNode" ' : '';
            var hasQuestion = treeNode.remarkFlag == '1' ? true : false;
            var nodeHTML = node(nodeId, roleId, testSignId, text, nodeType, hasChild, null, treeNode.control, hasQuestion,true,isRetest);
            html = html.concat([
                                '<tr>',
                                    '<td align="center"  ' + nodeDomId + '  class="nodeTd ' + nodeType + ' ' + nodeCss + '">',
                                        nodeHTML,
                                    '</td>'
                            ]);
            
            	 
            

            if (hasChild) {
                html = html.concat([
                            '<td class="node" style="display:' + (treeNode.control ? 'none' : '') + '">',
                                createTree(treeNode.children , false),
                            '</td>'
                        ]);
            }
            html = html.concat(['</tr>']);
        });
        html.unshift('<table cellSpacing="0" cellPadding="0"  border="0">');
        html.push('</table>');
        return html.join('');
    }

    var bindEvents = function() {

        //取消默认右击事件
        document.oncontextmenu = function(e){
            var event = e || window.event;
            baidu.event.stopPropagation(event);
            return false; 
        }

        var getNowEvent = function(target, evt){
            var type = null;
            if(evt.button == 0) {
                if(baidu.dom.hasClass(target, 'move_handle')) {
                    type = 'move';
                }
                else if(baidu.dom.hasClass(target, 'retest_handle')) {
                    type = 'retest';
                }
                else if (baidu.dom.hasClass(target, 'marker')) {
                    type = 'marker';
                }
                else if (baidu.dom.hasClass(target, 'message_delete')) {
                    type = 'deleteMessage';
                }
                else if(baidu.dom.hasClass(target, 'delete_handle')) {
                    type = 'delete';
                }
                else if(baidu.dom.hasClass(target, 'nodeText')) {
                    type = 'nodeTextChange';
                }
                else if(baidu.dom.hasClass(target, 'taskText')) {
                    type = 'nodeTextChange';
                }            
                else if(baidu.dom.hasClass(target, 'role')) {
                	if(baidu.dom.hasClass(target, 'tmp')){
                		type = '';
                	}else{
                		type = 'role';
                	}
                    
                }
                else if(baidu.dom.hasClass(target, 'testSign')) {
                    type = 'test';
                }
                else if(baidu.dom.hasClass(target, 'aSignRole')) {
                    type = 'roleSign';
                }
                else if(baidu.dom.hasClass(target, 'aSignRoleName')) {
                    type = 'roleSign';
                }                
                else if(baidu.dom.hasClass(target, 'aSign')) {
                    type = 'roleSign';
                }                 
                else if(baidu.dom.hasClass(target, 'aTestSign')) {
                    type = 'testSign';
                }
                else if(baidu.dom.hasClass(target, 'aTestSignImg')) {
                    type = 'testSign';
                }                
                else if(baidu.dom.hasClass(target, 'aTestSignText')) {
                    type = 'testSign';
                }     
                else if(baidu.dom.hasClass(target, 'child_control_handle')) {
                    type = 'childControl';
                }                 }
            else if(evt.button == 2) {
                type = 'addNode';

            }

            return type;
        }

        baidu.on(window, 'ondblclick', function(e) {
            var evt = e || window.event;
            var target = baidu.event.getTarget(evt);
            var hasClass = baidu.dom.hasClass;
            var getAttr = baidu.dom.getAttr;
            if(locked) {
                return ;
            }
            editFlag = true;
            baidu.dom.hide('testSign');
            baidu.dom.hide('signet');
            var eventType = getNowEvent(target, evt);
            switch(eventType) {//此分支是修改节点文本事件
                case 'nodeTextChange':
                    var id = getAttr(target, 'textId'); 
                    nowAtNodeId = id;
                    opEvents.changeNodeText(id, target);
                    break;
            }
        });
        
        baidu.on(window, 'click', function(e) {
            var evt = e || window.event;
            var target = baidu.event.getTarget(evt);
            var hasClass = baidu.dom.hasClass;
            var getAttr = baidu.dom.getAttr;
            if(locked) {
                return ;
            }
            opEvents.clearSelectNode();
            baidu.dom.hide('testSign');
            baidu.dom.hide('signet');
            editFlag = false;
            var eventType = getNowEvent(target, evt);
            switch(eventType) {
                case 'delete':
                    var id = getAttr(target, 'deleteId');
                    nowAtNodeId = id;
                    opEvents.deleteNode(id);
                    break;
                case 'deleteMessage':
                    var nodeId = getAttr(target, 'nodeId');
                    var id = getAttr(target, 'messageId');
                    opEvents.deleteMessage(nodeId, id);
                case 'deleteAppraise':
                    var nodeId = getAttr(target, 'nodeId');
                    var id = getAttr(target, 'messageId');
                    opEvents.deleteAppraise(nodeId, id);
                //此分支是修改节点文本事件
                case 'nodeTextChange':
                    var id = getAttr(target, 'textId'); 
                    nowAtNodeId = id;
                    opEvents.selectNode(id, target);
                    break;
                case 'marker':
                	var id = getAttr(target, 'nodeId');
                	var nodeData = utils.findNodeData(treeData,id);
                	if(nodeData==null){
                		break;
                	}
                    var hasQuestion = hasClass(target, 'questionMark');
                    opEvents.viewMessage(id, hasQuestion);
                    break;
                case 'role':
                    var id = getAttr(target, 'nodeId'); 
                    nowAtNodeId = id;
                    opEvents.changeRole(id, target);
                    break;
                case 'test':
                    var id = getAttr(target, 'nodeId'); 
                    nowAtNodeId = id;
                    opEvents.changeTest(id, target);
                    editFlag = true;
                    break;
                case 'roleSign':
                	
                    var nodeId = nowAtNodeId;
                    var roleId = getAttr(target, 'roleId');
                    opEvents.changeRoleSign(nodeId, roleId);
                    break;
                case 'childControl':
                    var nodeId = getAttr(target, 'controlId');    
                    opEvents.changeChildrenStatus(target, nodeId);
                    break;   
                case 'retest':
                    var nodeId = getAttr(target, 'retestId');    
                    opEvents.changeRetestStatus(target, nodeId);
                    break; 
                default : 
                    break;
            }
            baidu.event.stopPropagation(evt);
        });

        baidu.on(window, 'mousedown', function(e) {
            var evt = e || window.event;
            var target = baidu.event.getTarget(evt);
            if(locked) {
                return false;
            }
            if(evt.button == 2) {
            	baidu.g('loading').style.display='';
                request.dao.addNode(projectId, storyId ,{}, function(nodeId){
                	if(nodeId==0){
                		utils.alert('提示信息', '其他人正在编辑，请等待编辑完成');
                		return;
                	}
                    newNode = node(nodeId, 'choose', null, '', '', false, true, false, false,0);
                    var pos = { 
                        left : baidu.event.getPageX(evt),
                        top : baidu.event.getPageY(evt)
                    };
                    baidu.dom.setAttr(newNode, 'id', 'newNode');
                    newNode.style.position = 'absolute';
                    newNode.style.left = pos.left + 'px';
                    newNode.style.top = ( pos.top - 58 ) + 'px';

                    newNodeData = {
                        'id' : nodeId,
                        'text' : '',
                        'roleId' : '0',
                        'isRetest' : 0,
                        'remarkFlag' : '0',
                        'children' : []
                    };
                    baidu.g('newNode') && containner.removeChild(baidu.g('newNode'));
                    containner.appendChild(newNode);
                    baidu.g('loading').style.display='none';
                });
            }
            else if(evt.button == 0) {
                if(baidu.dom.hasClass(target, 'taskText') || baidu.dom.hasClass(target, 'nodeText') ) {
                    var textNode = (baidu.dom.hasClass(target, 'taskText') && baidu.dom.getParent(target)) || target;  
                    
                    moveToLeft = evt.offsetX;
                    moveToTop = evt.offsetY;
                    
                    var pos = { 
                        left : baidu.event.getPageX(evt) - moveToLeft,
                        top : baidu.event.getPageY(evt) - moveToTop - 58
                    };

                    startPointX = baidu.event.getPageX(evt);
					startPointY = baidu.event.getPageY(evt);
                    var nodeId = baidu.dom.getAttr(textNode, 'textId');
                    var nodeData = utils.findNodeData(treeData, nodeId) || newNodeData;
                    var nodeId = nodeData.id;
                    var roleId = nodeData.roleId;
                    var testSignId = nodeData.testSignId;
                    var hasChild = !!nodeData.children.length;
                    var text = nodeData.text || '';
                    nodeData.control = nodeData.control ? true : false; 
		    var hasQuestion = nodeData.remarkFlag == '1' ? true : false;
	            var isRetest = nodeData.isRetest;
                    var nodeHTML = node(nodeId, roleId, testSignId, text, '', hasChild, false, nodeData.control,true,true,isRetest);

                    movedDiv = node(nodeId, roleId, testSignId, text, '', hasChild, true, false,hasQuestion,true,isRetest);
                    baidu.dom.addClass(movedDiv, 'movedDiv');
                    movedDiv.style.position = 'absolute';
                    movedDiv.style.left = pos.left + 'px';
                    movedDiv.style.top = pos.top + 'px';
                    movedDiv.style.display = 'none';
                    movedDiv.style.display = 'none';

                    containner.appendChild(movedDiv);
                }
                mousedown = true;
            }
        });
        baidu.on(window, 'mouseup', function(e) {
            var evt = e || window.event;
            var target = baidu.event.getTarget(evt);
            if(locked) {
                return false;
            }
            if(movedDiv && mousedown == true) {
                var x = baidu.event.getPageX(evt);
                var y = baidu.event.getPageY(evt);;

                var needChangeObj = null;
                baidu.each(baidu.dom.query('.nodeTd'), function(item) {
                    var pos = baidu.dom.getPosition(item);
                    var itemX = pos.left;
                    var itemY = pos.top;
                    var width = item.offsetWidth;
                    var height = item.offsetHeight;
                    if(x >= itemX && y >= itemY && x <= itemX + width && y <= itemY + height) {
                        needChangeObj = item;
                    }
                    
                });
                if(needChangeObj) {
                    needChangeObj = baidu.dom.first(needChangeObj);
                    var needChangeId = baidu.dom.getAttr(needChangeObj, 'textId');
                    var needChangeData = utils.findNodeData(treeData, needChangeId);

                    var nowId = baidu.dom.getAttr(movedDiv, 'textId');
                    var nowData = utils.findNodeData(treeData, nowId);

                    if(needChangeId == nowId || (nowData && utils.isParentNode(nowData.children, needChangeId))) {
                        containner.removeChild(movedDiv);
                        movedDiv = null;
                        mousedown = false;
                        return false;
                    }

                    //修改节点位置callback 
                    var changeCallback = function() {
                        var needChangeData = utils.findNodeData(treeData, needChangeId);
                        var needChangeDataCon = utils.findNodeContainer(treeData, needChangeId);
                        var nowData = utils.findNodeData(treeData, nowId);
                        var nowDataCon = utils.findNodeContainer(treeData, nowId);

                        needChangeData.children = needChangeData.children || [];

                        if(!baidu.array.contains(needChangeData.children, nowData)) {
                            if(nowDataCon) {
                                needChangeData.children.push(nowData);
                                baidu.array.remove(nowDataCon, nowData);
                            }
                            else {
                                 needChangeData.children.push(newNodeData);
                                 newNodeData = null;
                                 newNode && containner.removeChild(newNode) && (newNode = null);
                            }
                        }
                        repaintTree();
                    };
                    baidu.g('loading').style.display='';
                    request.dao.updateNode(projectId,storyId,nowId,'position',{
                        'parentId' : needChangeId
                    }, function(id){
                    	if(id==0){
                    		utils.alert('提示信息', '其他人正在编辑，请等待编辑完成');
                    		baidu.g('loading').style.display='none';
                    		return;
                    	}else{
                    		changeCallback();
                    	}
                    	baidu.g('loading').style.display='none';
                    }, function(status, statusObj){
                        defaultError(status, statusObj);
                    });
                }
            }
            movedDiv&&containner.removeChild(movedDiv)&&(movedDiv=null);
            mousedown = false;
        });

        baidu.on(window, 'mousemove', function(e) {
            var evt = e || window.event;

            if(locked) {
                return false;
            }

            if(movedDiv && mousedown == true) {
                var pos = {
                    left : baidu.event.getPageX(evt) - moveToLeft + 'px',
                    top : baidu.event.getPageY(evt) - moveToTop - 58 + 'px'
                }
                if ((Math.abs(baidu.event.getPageX(evt) - startPointX) >= 2) || (Math.abs(baidu.event.getPageY(evt) - startPointY) >= 2)){
	                movedDiv.style.display = '';
	                baidu.dom.setStyles(movedDiv, pos);
                }
            }
        });
        baidu.on(window, 'mouseover', function(e) {
            var evt = e || window.event;
            var target = baidu.event.getTarget(evt);
            if(locked) {
                return false;
            }
            var node = target ;
            var eventType = getNowEvent(target, evt);
            if(eventType == 'childControl') {
                baidu.event.stopPropagation(evt);
                return ;
            }
            if(node&&(((node = target)&&baidu.dom.hasClass(node, 'nodeText'))||((node = baidu.dom.getParent(target))&&baidu.dom.hasClass(node, 'nodeText')))) {
                baidu.dom.addClass(node, 'nodeText-hover');
            }
        });
        baidu.on(window, 'mouseout', function(e) {
            var evt = e || window.event;
            var target = baidu.event.getTarget(evt);
            
            if(locked) {
                return false;
            }

            baidu.each(baidu.dom.query('.nodeText-hover'), function(item){
                baidu.dom.removeClass(item, 'nodeText-hover');
            });
        });
        baidu.on(document.body, 'onselectstart', function(e) {
            var seletedNodes = baidu.dom.query('.nodeText-active');
            baidu.each(seletedNodes, function(item) {
                baidu.dom.removeClass(item, 'nodeText-active');
            });
        });
        baidu.on(document.body, "copy", function(e) {

            if(locked) {
                   return false;
               }
               
            var evt = e || window.event;
            var clipboardData = (evt.clipboardData || window.clipboardData);
            var text = clipboardData.getData("text");

            var seletedNodes = baidu.dom.query('.nodeText-active');
            if(seletedNodes.length) {
                document.selection && document.selection.empty && (document.selection.empty(), 1) 
                || window.getSelection && window.getSelection().removeAllRanges();
                
                var seletedNode = seletedNodes[0];
                //var selectionText = window.getSelection();
                var seletedNodeId = baidu.dom.getAttr(seletedNode, 'textId');
                needCopyNodeId = seletedNodeId;
                opEvents.clearSelectNode();
            }
            else{
                needCopyNodeId = null;
                
            }

        });
      
        baidu.on(document.body, "paste", function (e) {
            if(locked) {
               return false;
           	}

            if(needCopyNodeId) {
                var nodeEle = null;

                baidu.each(baidu.dom.query('.nodeText-active'), function(item){
                    nodeEle = item;
                });
                
                if(!nodeEle) {
                    utils.alert('提示信息', '您剪贴版里没有要复制的节点');
                    return false; 
                }
                var parentId = baidu.dom.getAttr(nodeEle, 'textId');
                baidu.g('loading').style.display='';
                request.dao.copyNode(projectId,storyId ,needCopyNodeId,parentId, function(data){
                	if(data.id==null){
                		crmSign.tree.utils.alert('其他人正在编辑，请稍后重试');
                		return;
                	}
                    var nodeContainer = utils.findNodeData(treeData, parentId);
                    nodeContainer.children = nodeContainer.children || [];
                    nodeContainer.children.push(data);
                    repaintTree();
                    needCopyNodeId = null;
                    baidu.g('loading').style.display='none';
                });
            }
            else if(!editTextBox) {
                var evt = e || window.event;
                var clipboardData = (evt.clipboardData || window.clipboardData);
                var text = clipboardData.getData("text");

                if(!text) {
                    utils.alert('提示信息', '您剪贴板里没数据');
                    return false; 
                }

                var nodeEle = null;
                baidu.each(baidu.dom.query('.nodeText-active'), function(item){
                    nodeEle = item;
                });

                if(nodeEle) {
                    var nodeId = baidu.dom.getAttr(nodeEle, 'textId');
                    var nodeData = utils.findNodeData(treeData, nodeId);
//粘贴创建
                    baidu.g('loading').style.display='';
                    request.dao.addNode(projectId, storyId, {'parentId': nodeId,'nodeText':text }, function(id){
                    	if(id==0){
                    		utils.alert('提示信息', '其他人正在编辑，请等待编辑完成');
                    		baidu.g('loading').style.display='none';
                    		return;
                    	}
                    	var newNodeData = {
                            'id' : id,
                            'text' : text,
                            'roleId' : '0',
                            'remarkFlag': 0,
                            'isRestart' : 0,
                            'children' : []
                        };
                        nodeData.children.push(newNodeData);
                        repaintTree();
                        baidu.g('loading').style.display='none';
                    });

                } 
                else {
                	if(masker==null){
                    	utils.alert('提示信息', '您还未选中要被添加的节点');
                	}
                }
            }
        });


        baidu.on(window, 'keyup', function(e) {
            var evt = e || window.event;
			if(locked) {
				return false;
			}
			//insert
			if(evt.keyCode == 45){
				if(locked) {
	                 return false;
	             }
	        	 var seletedNodes = baidu.dom.query('.nodeText-active');
	        	 if(seletedNodes.length){
	        		 var seletedNode = seletedNodes[0];
	        		 var parentId = baidu.dom.getAttr(seletedNode, 'textId');
	        		 baidu.g('loading').style.display='';
	        		 request.dao.addNode(projectId,storyId,{'parentId': parentId}, function(id){
	        			 if(id==0){
	                 		utils.alert('提示信息', '其他人正在编辑，请等待编辑完成');
	                 		baidu.g('loading').style.display='none';
	                 		return;
	                 	}
	        			 var newNodeData = {
	                         'id' : id,
	                         'text' : '',
	                         'roleId' : '0',
	                         'remarkFlag': 0,
	                         'isRestart' : 0,
	                         'children' : []
	                     };
	                     var nodeData = utils.findNodeData(treeData, parentId);
	                     nodeData.children.push(newNodeData);
	                     repaintTree();
	                     baidu.g('loading').style.display='none';
	                 });
	        	 }
			}
			//enter
			if(evt.keyCode == 13){
				if(locked) {
	                 return false;
	             }
	        	 var seletedNodes = baidu.dom.query('.nodeText-active');
	        	 
	        	 if(seletedNodes.length && !editFlag){
	        		 var seletedNode = seletedNodes[0];
	        		 var nodeId = baidu.dom.getAttr(seletedNode, 'textId');
	        		 baidu.g('loading').style.display='';
	        		 request.dao.createBrotherNode(projectId,storyId,nodeId,function(data){
	        			 if(data.nodeId==null){
	                 		crmSign.tree.utils.alert('其他人正在编辑，请稍后重试');
	                 		return;
	                 	}
	        			 var newNodeId = data.nodeId;
	                     var newNodeData = {
	                         'id' : newNodeId,
	                         'text' : '',
	                         'roleId' : '0',
	                         'remarkFlag': 0,
	                         'isRestart' : 0,
	                         'children' : []
	                     };
	                     var nodeData = utils.findNodeData(treeData, data.parentId);
	                     var size = nodeData.children.length;
	                     var tempChildren = [];
	                     for(var i=0; i<size; i++){  
	                         tempChildren.push(nodeData.children[i]);
	                         if(nodeData.children[i].id==nodeId){
	                             tempChildren.push(newNodeData);
	                         }
	                     }
	                     nodeData.children = tempChildren;
	                     repaintTree();
	                     baidu.g('loading').style.display='none';
	                 });
	        	 }
			}
            //上 38 下 40
            if(evt.keyCode == 38 || evt.keyCode == 40) {
                var indicatorFlag = (evt.keyCode == 38 ? -1: 1);
                if(baidu.dom.query('.nodeText-active').length) {
                    var selectedNode = baidu.dom.query('.nodeText-active')[0];
                    var id = baidu.dom.getAttr(selectedNode, 'textId');
                    var selectedNodeData = utils.findNodeData(treeData, id);
                    var selectedNodeDataCon = utils.findNodeContainer(treeData, id);
                    var allNum = selectedNodeDataCon.length;
                    var nowAt = baidu.array.indexOf(selectedNodeDataCon, selectedNodeData);
                    var needChange = nowAt + indicatorFlag;
                    var needChangeData = selectedNodeDataCon[needChange];

                    if(nowAt == 0 && indicatorFlag == -1) {
                        return false;
                    }

                    if((nowAt == allNum - 1) &&  indicatorFlag == 1) {
                        return false;
                    }
                    baidu.g('loading').style.display='';
                    request.dao.updateNode(projectId,storyId,id,'seq',{'direction': indicatorFlag}, function() {
                        selectedNodeDataCon[needChange] = selectedNodeData;
                        selectedNodeDataCon[nowAt] = needChangeData;
                        repaintTree();
                        baidu.each(baidu.dom.query('.nodeText'), function(item) {
                            if(baidu.dom.getAttr(item, 'textId') == id) {
                                baidu.dom.addClass(item, 'nodeText-active');
                            }
                        });
                        baidu.g('loading').style.display='none';
                    });
                }
            }
        });
    };

    var initTree = function() {
        treeContainner = baidu.g('treeContainner');
        containner = baidu.g('containner');
        createTestSignLayer();
        baidu.g('loading').style.display='';
        request.dao.listRoles(projectId,function(roles){
            roles.unshift({'roleId':'0','name':'请选择'},{'roleId':'-1','name':'需QA签章'},{'roleId':'-3','name':'需PM签章'},{'roleId':'-2','name':'准入签章'});
            baidu.each(roles, function(item) {
                rolesMap[item.roleId + ''] = item['roleTag'];
            });
               createSignLayer(roles);

            request.dao.listNodes(projectId,storyId,function(data){
                treeData = data;
                repaintTree();
                bindEvents();
                baidu.g('loading').style.display='none';
            });
            
        }, function(status, statusObj) {
            defaultError();
        });
    };
    
    var repaintTree = function() {

        document.body.appendChild(baidu.g('testSign'));
        document.body.appendChild(baidu.g('signet'));
        baidu.g('messsageBoxMasker') && document.body.appendChild(baidu.g('messsageBoxMasker'));
        treeContainner.innerHTML = createTree(treeData, true);
        if(newNode) {
            var pos = {
                left:newNode.style.left,
                top:newNode.style.top
            }
            var nodeId = newNodeData.id;
            var roleId = newNodeData.roleId;
            var testSignId = newNodeData.testSignId;
            var isRetest = newNodeData.isRetest;
            var text = newNodeData.text.replace(/\n/g, '<br />');
            var hasChild = !!newNodeData.children.length;
            newNodeData.control = newNodeData.control ? true : false;
            var hasQuestion = newNodeData.remarkFlag == '1' ? true : false;
            containner.removeChild(newNode);
            newNode = node(nodeId, roleId, testSignId, text, '', hasChild, true,newNodeData.control,hasQuestion,true,isRetest);
            baidu.dom.setAttr(newNode, 'id', 'newNode');
            newNode.style.position = 'absolute';
            newNode.style.left = pos.left;
            newNode.style.top = pos.top;
            containner.appendChild(newNode);
        }
        treeContainner.appendChild(baidu.g('testSign'));
        treeContainner.appendChild(baidu.g('signet'));
        baidu.g('messsageBoxMasker') && treeContainner.appendChild(baidu.g('messsageBoxMasker'));
    }
    var lockTree = function(){
        locked = true;

    }
    var unLockTree = function(){
        locked = false;
    }
    return {
        'createTree' : createTree,
        'initTree' : initTree,
        'lockTree' : lockTree,
        'unLockTree' : unLockTree,
        'utils' : utils
    };
})();

function initUser(){
	request.dao.getUser(function(data){
		baidu.g('login_user').innerHTML=data.user;
	});
}

function escape2Html(str) {
	 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"','#034':'"'};
	 return str.replace(/&(lt|gt|nbsp|amp|quot|#034);/ig,function(all,t){return arrEntities[t];});
	}

window.onload = function(){
    var ua = navigator.userAgent.toLowerCase();
    projectId = baidu.url.getQueryValue(window.location.href,'projectId');
    storyId = baidu.url.getQueryValue(window.location.href,'storyId');
    projectName = baidu.url.getQueryValue(window.location.href,'projectName');
    cardName =  baidu.url.getQueryValue(window.location.href,'cardName');
    
    initUser();
    
    var projectIdInput = null;
    if (!projectIdInput) {
        projectIdInput = baidu.dom.create('input', {
            'id' : 'projectId',
            'type' : 'hidden',
            'value' : projectId,
            'text' : projectId
        });
    }    
    baidu.g('importForm').appendChild(projectIdInput);
    
    var storyIdInput = null;
    if (!storyIdInput) {
        storyIdInput = baidu.dom.create('input', {
            'id' : 'storyId',
            'type' : 'hidden',
            'value' : storyId,
            'text' : storyId
        });
    }    
    baidu.g('importForm').appendChild(storyIdInput);
    
    crmSign.tree.initTree();
    
    request.dao.querySubmit(projectId,storyId,function(data){
        if(data == false){
            baidu.g('submitBtn').innerHTML="提测";
        }else{
            baidu.g('submitBtn').innerHTML="已提测";
        }
    }, function(status, statusObj) {
        defaultError();
    });
    baidu.g('importBtn').onclick = function(){
    	alert("预计12.7开放");
    };
    baidu.g('submitBtn').onclick = function() {
    	//storystatus = baidu.g('status').innerText;
        if(baidu.g('submitBtn').innerHTML == '提测') {
        	request.dao.submit(projectId,storyId,function(data){
                baidu.g('submitBtn').innerHTML="已提测";
            }, function(status, statusObj) {
                defaultError();
            });
          
        }
        else {
        	request.dao.cancelSubmit(projectId,storyId,function(data){
                baidu.g('submitBtn').innerHTML="提测";
            }, function(status, statusObj) {
                defaultError();
            });
        }
    };
    
    /*baidu.g('batchBtn').onclick = function() {
        baidu.g('masker').style.display="";
        crmSign.tree.lockTree();
        baidu.g('btn_cancel_change').onclick = function() {
            baidu.g('masker').style.display="none";
            crmSign.tree.unLockTree();
        };
        baidu.g('btn_ok_change').onclick = function() {
            baidu.g('importForm').action="node/batchSave.action?projectId="+projectId+"&storyId="+storyId;
            baidu.g('importForm').submit();
            setTimeout(function(){window.location.reload()},3000);
        };
    };*/
   /* //模板下载
    baidu.g('eDownloadTemplate').onclick = function() {
    	baidu.g('temp_download_id').action="node/download.action?type=1&fileName=template.xls&projectId="+projectId+"&storyId="+storyId;
    	baidu.g('temp_download_id').submit();
    };
    //case下载
    baidu.g('story_down_flag').onclick = function() {
        baidu.g('story_down_form').action="node/download.action?type=2&fileName=case_"+projectId+"_"+storyId+".csv&projectId="+projectId+"&storyId="+storyId;
        baidu.g('story_down_form').submit();
    };
    */
    baidu.g('loading').style.display='';
    request.dao.queryStoryDetail(projectId, storyId, projectName ,function(data){
        //todo
    	if(data.result=='fail'){
			alert("请将sfcrmqasign用户加入项目空间只读用户组，若已加入仍能看到此提示，请联系hi：suhanyuan");
			baidu.g('loading').style.display='none';
			return;
		}
        baidu.g('title_text').innerHTML = data.title;
        var info = data.detail.replace(/\$\{basePath\}/ig,'http://icafe.baidu.com:80/');
        baidu.g('detail_text').innerHTML = escape2Html(info);
        baidu.g('loading').style.display='none';
    }, function(status, statusObj) {
        defaultError();
    });
    
    request.dao.queryProperty(projectId, function(data){
    	muliRoleFlag = data.muli_signet;
    	bugSpaceId = data.bug_space_id;
    });
    baidu.g('newBugBtn').onclick = function()	{
    	if(bugSpaceId!=''){
    		window.open(bugSpaceId);
    	}else{
    		alert("请先配置新建bug路径");
    	}
    };
    baidu.g('showDetailBtn').onclick = function(){
        if(baidu.g('showDetailBtn').innerHTML == '显示详情') {
            //执行打开
        	baidu.g('cardDetail').style.height = "45%";
            baidu.g('cardDetail').style.display = "";
            baidu.g('showDetailBtn').innerHTML = '收起详情';
            baidu.g('treeContainner').style.height = "55%";

        }else{
            //执行收起
            document.getElementById('cardDetail').style.display = "none";
            baidu.g('showDetailBtn').innerHTML = '显示详情';
            document.getElementById('treeContainner').style.height = "100%";
        }
    };
    baidu.g('editBtn').onclick = function() {
        if(baidu.g('editBtn').innerHTML == '编辑') {
            request.dao.applyToEdit(projectId,storyId, function(data){
                if(data.cardCanEdit) {
                    crmSign.tree.unLockTree();
                    baidu.g('editBtn').innerHTML = '锁定';
                    
                }
                else {
                	if(data.editUserName!=""){
                		crmSign.tree.utils.alert('当前' + data.editUserName + '正在编辑，请稍后重试');
                	}else{
                		crmSign.tree.utils.alert('未能获得登录人信息，请登录后重试');
                	}
                    
                }
                baidu.g('loading').style.display='none';
            }, function(){
                crmSign.tree.utils.alert('提示信息','不能编辑');
            });
        }
        else {
            request.dao.releaseCardEditLock(projectId,storyId, function(){
                crmSign.tree.lockTree();
                baidu.g('editBtn').innerHTML = '编辑';
            }, function(){
                crmSign.tree.utils.alert('提示信息', '后台异常，请刷新后重试');
            });
        }
    };

/*
    baidu.g('introduceBtn').onclick = function() {
        if(baidu.g('introduceLayer').style.display == 'none') {
            baidu.dom.show('introduceLayer');
        }
        else {
            baidu.dom.hide('introduceLayer');
        }
    };*/
    /*baidu.g('closeLayer').onclick = function() {
        baidu.dom.hide('introduceLayer');
    };
    baidu.g('closescoreLayer').onclick = function() {
        baidu.dom.hide('scoreLayer');
    };*/
    
}
