// var senderIdentifier;
//
// $(document).ready(function () {
//     createEmptyTable();
// });
//
// $("#selfile").on("change", function (e) {
//     getExcelFile();
// });
//
// function createEmptyTable() {
//     const data = [{column1: '', column2: ''}];
//     pageTemplating(data);
//     $('#validation').html('');
// }
//
// function pageTemplating(totaldata) {
//     let createdTable = createTable(totaldata); // 일단 전부 렌더링
// }
//
// function createTable(data) {
//     $("#selfile")[0].value = ""; //가져왔던 엑셀 정보 리셋
//     new Handsontable($('#handson')[0]).destroy(); //기존 테이블 삭제
//
//     /*        console.log("테이블 생성할 데이터", data); */
//
//     const table = new Handsontable($('#handson')[0], {
//         data: data,
//         rowHeaders: true,
//         colHeaders: [column1, column2],
//         stretchH: 'all',
//         search: true,
//         renderAllRows: true,
//         allowInsertRow: true,
//         allowInsertCol: false,
//         contextMenu: {
//             callback: function (key, selection, clickEvent) {
//                 $("#totalCnt").html("<h5 class='pull-right'>총 " + (this.countRows()) + "명</h5>");
//             },
//             items: {
//                 "row_above": {
//                     name: 'add a row above'
//                 },
//                 "remove_row": {
//                     disabled: function () {
//                         return this.countRows() <= 1; //잔여 row 가  1이하일때 disable
//                     },
//                     name: 'remove this row'
//                 }
//             }
//         },
//         height: 400
//     });
//
//     $("#totalCnt").html("<h5 class='pull-right'>총 " + (table.countRows()) + "명</h5>");
//
//     //테이블 생성하면서 검색기능 같이 붙여줌...
//     $('#searchingField')[0].value = '';
//     $('#search_result').html('');
//
//     let searchingField = $('#searchingField')[0];
//
//     Handsontable.dom.addEvent(searchingField, 'keyup', function (event) {
//         const search = table.getPlugin('search');
//         const queryResult = search.query(this.value);
//         /*            console.log("검색결과", queryResult);*/
//
//         table.render();
//
//         let tempStr = '';
//         for (let result of queryResult) {
//             if (result.col == 0) { //0열 = 전화번호만..
//                 tempStr += '<tr><td>' + (result.row + 1) + '</td><td>' + result.data + '</td></tr>';
//             }
//         }
//
//         $('#search_result').html(tempStr);
//     });
//
//
//     return table;
// }
//
//
// function saveTemplate(messageobj) {
//     $.ajax({
//         url: templateurl + sessionedParams,
//         type: 'post',
//         data: JSON.stringify(messageobj),
//         contentType: 'application/json; charset=utf-8',
//         success: function (data) {
//             alert(data['message']);
//         },
//         error: function (data) {
//             alert(JSON.stringify(data));
//             console.log(JSON.stringify(data));
//         }
//     });
// }
//
// function callTemplate() {
//     $.getJSON(templateurl + sessionedParams, '', function (data) {
//         // console.log(JSON.stringify(data));
//         $('#template_list_area').html('');
//         data.forEach(d => $('#template_list_area').append('<tr data-dismiss="modal"><td><button class="btn btn-primary-outline btn-sm" onclick="delTemplate(' + d.templateIdx + ')">삭제</button></td><td onclick="getTemplate(' + d.templateIdx + ')" style="cursor: pointer">' + d.templateName.substr(0, 13) + '</td></tr>'));
//     }).fail(function (data) {
//         console.log(JSON.stringify(data));
//     });
// }
//
// function getTemplate(idx) {
//     $.getJSON(templateurl + '/' + idx + sessionedParams, '', function (data) {
//         // console.log(JSON.stringify(data));
//         $('#messageName').val(data.templateName);
//         $('#message').val(data.template);
//     }).fail(function (data) {
//         console.log(JSON.stringify(data));
//     });
// }
//
// function delTemplate(idx) {
//     $.ajax({
//         url: templateurl + "/" + idx   + sessionedParams,
//         type: 'delete',
//         success: function (data) {
//             alert(data['message']);
//         },
//         error: function (data) {
//             console.log(JSON.stringify(data));
//         }
//     });
//
// }
//
// function numCheck(str) {
//     const pattern = /(^\d{3})-(\d{3,4})-(\d{4})$/g;
//     return pattern.test(str);
// }
//
//
// function resetMessageArea() {
//     $('#messageName').val('');
//     $('#message').val('');
// }
//
// function getCallbackFromTable(){
//     // let callback = $.trim($("#callback").val());
//     let callback = $("#callback option:selected").val();
//
//     if(callback != ''){
//         return callback;
//     } else {
//         return false;
//     }
// }
//
// function getMessageDataFromTable() {
//
//     let name = $.trim($("#messageName").val());
//     let content = $.trim($("#message").val());
//     let stringByte = checkStrByte(name) + checkStrByte(content);
//
//     if (name != '' && content != '' && stringByte < 2000) { //메시지 내용 및 제목이 비어선 안되고 합쳐서 2000바이트 이하일것
//         return {
//             templateName: name,
//             template: content
//         };
//     } else {
//         return false;
//     }
//
//
// }
//
// function getUserDataFromTable() {
//     const users = XLSX.utils.table_to_book($("#handson table")[0]); // JQuery Obj [0] -> html dom obj
//     const json = XLSX.utils.sheet_to_json(users.Sheets['Sheet1']);
//     /*        console.log("테이블에서 가져온 정보");
//             console.log(json.length + "개");
//             console.log(json);*/
//
//     $('#validation').html('');
//     let result = true;
//     let formCheck = true;
//     json.forEach((row, idx, arr) => {
//
//         /*            console.log(Object.keys(row).length);
//                     console.log(Object.keys(row));
//                     console.log("번호 밸리데이션 ", numCheck(row.phoneNum));*/
//
//         if (Object.keys(row).length != 3) { // " ","phoneNum","name" 의 세가지 키여야 하는데 일단 키 만으로 확인
//             formCheck = false;
//         }
//
//         if (!numCheck(row.phoneNum)) {
//             $('#validation').append(idx + 1 + ' ');
//             result = false;
//         }
//     });
//
//     const messageTemplate = getMessageDataFromTable();
//     const callback = getCallbackFromTable();
//
//     if (result && formCheck && json.length > 0 && messageTemplate != false && callback != false) {
//         completeSubmit(json, messageTemplate, callback)
//     } else if (json.length < 1) {
//         $('#validation').text("수신인이 없습니다");
//     } else if (messageTemplate == false) {
//         $('#validation').text("메시지 제목과 내용을 입력해야 합니다(2000byte 이하)");
//     } else if (!result) {
//         $('#validation').append('행의 전화번호가 올바르지 않습니다. (입력 예시 : 000-0000-0000)');
//     } else if (!formCheck) {
//         $('#validation').text("셀은 비워둘 수 없습니다. phoneNum, name 구성의 파일인지 확인하세요");
//     } else if(callback == false){
//         $('#validation').text("발신자 번호를 입력해야 합니다.");
//     }
// }
// function completeSubmit(json, messageTemplate, callback) {
//
//     let sendingObj = {};
//
//     /*        console.log("보내는 메세지 템플릿", messageTemplate);
//             console.log("보내는 유저 목록", JSON.stringify(json));
//             */
//
//     sendingObj['messageTemplate'] = messageTemplate;
//     sendingObj['userInfoList'] = json;
//
//     senderIdentifier = randomString();
//     sendingObj['senderIdentifier'] = senderIdentifier;
//
//     sendingObj['callback'] =  callback;
//
//     $('#progressState').attr("style", "width: 0%");
//     $('#progressStateEnd').hide();
//     $('#progressbarTrigger').click();
//     $('.submitRelated').attr('disabled', true); //전송 시작하면 서브밋 관련 불능화
//     //전송 컨펌 버튼에 disabled 적용하면 모달이 자동으로 dismiss 안돼서 그냥 전송 버튼에만 붙임
//
//     console.log("전송 데이터");
//     console.log(sendingObj);
//
//
//     $.ajax({
//         url: submiturl  + sessionedParams,
//         type: 'post',
//         data: JSON.stringify(sendingObj),
//         contentType: 'application/json; charset=utf-8',
//         success: function (data) {
//             let timer = setInterval(function () {
//                 $.ajax({
//                     url: rooturl + "/check/" + senderIdentifier  + sessionedParams,
//                     type: 'post',
//                     success: function (data) {
//
//                         if (data.result == true) {
//                             clearInterval(timer); //처리 완료되어야 인터벌 종료
//                             $('#progressState').attr("style", "width: " + data.data + "%");
//                             $('#progressStateText').text("완료되었습니다");
//                             $('#progressStateEnd').show();
//                             $('.submitRelated').attr('disabled', false); // 인터벌 끝나야 팔스
//                         } else {
//                             $('#progressState').attr("style", "width: " + data.data + "%");
//                             $('#progressStateText').text(data.message);
//                         }
//                     },
//                     error: function (data) {
//                         clearInterval(timer);
//                         $('.submitRelated').attr('disabled', false);
//                         console.log(JSON.stringify(data));
//                         alert(JSON.stringify(data));
//                     }
//                 });
//             }, 2000); // 2초간격으로 처리율 확인
//
//         },
//         error: function (data) {
//             alert(JSON.stringify(data));
//             console.log(JSON.stringify(data));
//         }
//     });
//
// }
//
//
// function getExcelFile() {
//     const fObj = $("#selfile")[0];
//
//     console.log(fObj.value);
//     if (fObj.value === '') {
//         alert('파일을 선택하세요');
//     } else {
//         console.log(JSON.stringify(fObj.files));
//         const selectedFile = fObj.files[0];
//         // console.log(selectedFile.path);
//         // console.log('Name :' + selectedFile.name + '/ Size : ' + selectedFile.size);
//
//         const reader = new FileReader();
//
//         reader.onload = function (evt) {
//             if (evt.target.readyState == FileReader.DONE) {
//                 let data = evt.target.result;
//                 data = new Uint8Array(data);
//
//
//                 // call 'xlsx' to read the file
//                 const workbook = XLSX.read(data, {type: 'array'});
//                 // console.log("workbook ", workbook);
//
//                 // json
//                 const json = XLSX.utils.sheet_to_json(workbook.Sheets['Sheet1']);
//                 // console.log('xlsx to json ', JSON.stringify(json));
//
//                 let container = document.getElementById('handson');
//                 pageTemplating(json);
//                 // console.log("result of create table");
//             }
//         };
//         reader.readAsArrayBuffer(selectedFile);
//     }
// }
//
// function checkStrByte(string){
//     let stringByteLength = (function(s,b,i,c){
//         for(b=i=0; c=s.charCodeAt(i++); b+=c>>11?3:c>>7?2:1);
//         return b;
//     })(string);
// /*    console.log(stringByteLength+"바이트입니다");*/
//     return stringByteLength;
// }
//
// function randomString() {
//     const chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
//     const string_length = 15;
//     let randomstring = '';
//     for (let i = 0; i < string_length; i++) {
//         const rnum = Math.floor(Math.random() * chars.length);
//         randomstring += chars.substring(rnum, rnum + 1);
//     }
//     return randomstring;
// }






/**
 * service_management
 * */
//
// function checkEmptyInput(str){
//     if($.trim(str) == '' || str == 'undefined' || str == 'null'){
//         return false
//     }else{
//         return true
//     }
// }
//
// function submitServicer(){
//
//     $('#errorarea').text('');
//
//     let svcName = $.trim($('#serviceName').val());
//     let cb =  $.trim($('#callback').val());
//
//     if(checkEmptyInput(svcName) &&  checkEmptyInput(cb)) {
//         let simpleServicer = {"servicerName": svcName, "callback": cb};
//         $.ajax({
//             type: 'post',
//             url: serviceurl,
//             data: JSON.stringify(simpleServicer),
//             contentType: 'application/json; charset=utf-8',
//             success: function (data) {
//                 console.log(JSON.stringify(data));
//                 alert(data.message);
//                 window.location.reload();
//
//             },
//             error: function (data) {
//                 alert(JSON.stringify(data));
//             }
//         });
//     }else{
//         $('#errorarea').text("서비스명과 전화번호를 모두 채우세요");
//     }
// }
//
// function deleteSelectedServicer(){
//
//     let servicerList = new Array();
//
//     $("input[name=check-inside]:checked").each(function(){
//
//         let name = $(this).parents('.item-row').find('span[class=serviceName]').text();
//         let cb = $(this).parents('.item-row').find('span[class=callback]').text();
//
//         let newServicer = {"servicerName" : name, "callback" : cb};
//         // console.log(newServicer);
//         servicerList.push(newServicer);
//     });
//     // console.log(JSON.stringify(servicerList));
//
//     if(servicerList.length<1){
//         alert("삭제할 할목을 선택하세요");
//     }else {
//
//         $.ajax({
//             type: 'delete',
//             url: serviceurl,
//             data: JSON.stringify(servicerList),
//             contentType: 'application/json; charset=utf-8',
//             success: function (data) {
//                 alert(data.message);
//                 window.location.reload();
//             },
//             error: function (data) {
//                 console.log(JSON.stringify(data));
//             }
//         });
//
//     }
//
//
// }

