var detachDiv;
var tableAttachDiv;
var tableFlag = 0;
var detachFlag = 0;
var eventFlag = 0;
var modulesFromServer = {};
var graphFlag = 0,
    tempSelectedStudent;

$(document).ready(function () {
    init();
    listenForStaffMenuClick();
    listenForAttendanceMenuClick();

    $('.logoutClass').on("click", function () {
        $.ajax({
            url: '/logout',
            type: 'get',
            dataType: 'text/html',
            success: function (param1, param2, param3) {

                //window.location.href = param1;
                //console.log(param1);
                //console.log("success executed");
            },
            complete: function (param1, param2, param3) {
                //me.callback1(param1, param2, param3);
                //console.log(param1,param2);
                window.location.href = param1.responseText;
            }
        });
    });

});
function home() {
    document.getElementById("workPlace").style.visibility = "hidden";
    $('#graphBtn').hide();
    $('#tableBtn').hide();
    $('#sort').hide();

}

function init() {
    document.getElementById("workPlace").style.visibility = "hidden";
    $('#graphBtn').hide();
    $('#tableBtn').hide();
    $('#sort').hide();
    $('#tableStudent').hide();
    $('#staffTable').hide();

    document.getElementById("studentSelect").style.display = "none";

    if (lecturerCategory != null && lecturerCategory.toLowerCase() != 'admin') {//admin
        $("#AddStaff").detach();
        $("#AddStudent").detach();
        $("#AddAttendance").detach();
        $("#viewStaffTable").detach();

    }

    $.ajax({
        url: '/programs',
        type: 'GET',
        dataType: 'json',
        success: function (result) {
            //modulesFromServer = result;
            var k = 0;
            for (var i = 0; i < result.length; i++) {
                if (result[i].hasOwnProperty("modules")) {
                    var modules = result[i].modules;
                    for (var j = 0; j < modules.length; j++) {
                        if (modules[j].hasOwnProperty("moduleTitle")) {
                            modulesFromServer[k] = modules[j].moduleTitle;
                            k++;
                        }
                    }
                }
            }
        }
    });
}
function cleanInput() {
    $('#input1').val('');
    $('#input2').val('');
    $('#input3').val('');
    $('#input5').val('');
    $('#input7').val('');
    $('#userName').val('');
    $('#userPassword').val('');
}
function disableInput() {
    $("#input1").prop('disabled', true);
    $("#input2").prop('disabled', true);
    $("#input3").prop('disabled', true);
    $("#inputGender").prop('disabled', true);
    $("#input5").prop('disabled', true);
    $("#drop").prop('disabled', true);
    $("#input7").prop('disabled', true);
    $("#session").prop('disabled', true);
    $("#userName").prop('disabled', true);
    $("#userPassword").prop('disabled', true);

}
function enableInput() {
    $("#input1").prop('disabled', false);
    $("#input2").prop('disabled', false);
    $("#input3").prop('disabled', false);
    $("#inputGender").prop('disabled', false);
    $("#input5").prop('disabled', false);
    $("#drop").prop('disabled', false);
    $("#input7").prop('disabled', false);
    $("#session").prop('disabled', false);
    $("#userName").prop('disabled', false);
    $("#userPassword").prop('disabled', false);
}
function Reattach() {
    $("#workPlace").html(detachDiv);
}


function listenForStaffMenuClick() {
    $('.addStaffClass').on("click", function () {
        eventFlag = 1;
        User();
        cleanInput();
        enableInput();

        $("#submitButton").text(" Add user");
        $("#title").text("ADD NEW USER");
        document.getElementById("I9").style.display = "block";
        document.getElementById("I10").style.display = "block";
        document.getElementById("userName").style.display = "block";
        document.getElementById("userPassword").style.display = "block";
        $('#lbl9').text("User Name: ");
        $('#lbl10').text("Password");
    });

    $('.modifyStaffClass').on("click", function () {


        eventFlag = 2;
        User();
        cleanInput();
        enableInput();

        $("#submitButton").text(" Commit Change");
        $("#title").text("MODIFY OR DELETE USER");
        $("#I1").css({"display": "block"});
        $("#input1").css({"display": "block"});

        var lecturer = JSON.parse(localStorage.getItem("lecturer"));
        if (lecturer == null) {
            $.ajax({
                url: '/staff?lecturerId=' + lecturerId,
                type: 'get',
                dataType: 'json',
                success: function (lecturer) {
                    localStorage.setItem("lecturer", lecturer);
                    fillStaffDetails(lecturer);
                }
            });
        } else {
            fillStaffDetails(lecturer);
        }

    });

    $('.viewAccClass').on("click", function () {
        eventFlag = 3;
        User();
        cleanInput();
        disableInput();

        $("#submitButton").text("No action");
        $("#title").text("VIEW USER INFORMATION");
        //var url = window.location.href.replace("home", "");
        //setTimeout(function () {
        $.ajax({
            url: '/staff?lecturerId=' + lecturerId,
            type: 'get',
            dataType: 'json',
            success: function (lecturer) {
                fillStaffDetails(lecturer);
                localStorage.setItem("lecturer", JSON.stringify(lecturer));
            }
        });
        // }, 2000);

    });
    $('.viewAllStaff').on("click", function () {
        document.getElementById("workPlace").style.visibility = "visible";
        if (detachFlag == 0) {
            detachDiv = $("#dataEntry").detach();
            detachFlag = 1;
        }
        $("#sort").hide();//css("display","none");
        $("#tableBtn").hide();//.css("display","none");
        $("#graphBtn").hide();//.css("display","none");

        if ($("#graph").css("display") == 'block') {
            $("#graph").hide();
        }
        $("#workPlace").empty();
        $('#staffTable').show();
        viewAllStaff.getNDisplayAllStaff();
    });
}
function listenForAttendanceMenuClick() {
    $('.addStudentClass').on("click", function () {
        eventFlag = 4;
        Attendance();
        cleanInput();
        enableInput();

        $("#input1").hide();
        $("#userName").hide();
        $("#userPassword").hide();
        $("#submitButton").text(" Add Record");
        $("#title").text("ADD STUDENT RECORD");

        var sel = $("#selectProgram"), moduleOptions = '', programOptions = '';
        programsFromServer = [];
        $.ajax({
            url: '/programs',
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                modulesFromServer = [];
                //var k = 0;
                for (var i = 0; i < result.length; i++) {
                    programsFromServer[i] = {
                        programName: result[i].programName,
                        programId: result[i].programId
                    };
                }

                for (i = 0; i < programsFromServer.length; i++) {
                    programOptions = programOptions + '<option>' + programsFromServer[i].programName + '</option>';
                }
                //for (i = 0; i < modulesFromServer.length; i++) {
                //    moduleOptions = moduleOptions + '<option>' + modulesFromServer[i] + '</option>';
                //}
                sel.find('option').remove().end();
                sel.append(programOptions);
                //$("#selectModule").append(moduleOptions);
            }
        });
        $("#module").css({"display": "block"});
    });
    $('.addAttendanceClass').on("click", function () {
        eventFlag = 5;
        Attendance();
        cleanInput();
        enableInput();

        document.getElementById("I6").style.display = "block";
        document.getElementById("I7").style.display = "block";
        document.getElementById("I8").style.display = "block";

        document.getElementById("I9").style.display = "block";
        $('#I9').show();
        $("#lbl9").text("Student Id: ");

        document.getElementById("submitButton").style.display = "block";
        $("#submitButton").text(" Add Attendance");
        $("#title").text("ATTENDANCE");
        $("#lbl5").text("Module: ");
        var sel = "#selectProgram", moduleOptions = '', programOptions = '';
        $.ajax({
            url: '/programs',
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                modulesFromServer = [];
                var k = 0;
                for (var i = 0; i < result.length; i++) {
                    if (result[i].hasOwnProperty("modules")) {
                        var modules = result[i].modules;
                        for (var j = 0; j < modules.length; j++) {
                            if (modules[j].hasOwnProperty("moduleTitle")) {
                                modulesFromServer[k] = {
                                    moduleId: modules[j].moduleId,
                                    moduleTitle: modules[j].moduleTitle
                                };
                                k++;
                            }
                        }
                    }
                }
                for (i = 0; i < modulesFromServer.length; i++) {
                    moduleOptions = moduleOptions + '<option value="'
                        + modulesFromServer[i].moduleId + '">' + modulesFromServer[i].moduleTitle + '</option>';
                }
                $("#selectProgram").find("option").remove().end().append(moduleOptions);
            }
        });

        document.getElementById("lbl6").textContent = "Status: ";
        var newOptions = {
            "Present": "Present",
            "Absent": "Absent"
        };

        var $el = $("#drop");
        $el.empty(); // remove old options
        $.each(newOptions, function (value, key) {
            $el.append($("<option></option>")
                .attr("value", value).text(key));
        });

        $("#lbl7").text("Date Taken");
        $("#lbl8").text("Session: ");

        document.getElementById("I10").style.display = "none";
        document.getElementById("studentSelect").style.display = "block";

        $.ajax({
            url: '/student',
            type: 'get',
            dataType: 'json',
            success: function (student) {
                var option = '';

                for (var i = 0; i < student.length; i++) {
                    option += '<option value="' + student[i].studentId + '">'
                        + student[i].firstName + "  " + student[i].lastName + '</option>';
                }
                var selectAttendance = $('#studentSelect');
                selectAttendance.append(option);
                selectAttendance.on("change", function () {

                    var selected = $(this).val(), selectedStudent;
                    for (var i = 0; i < student.length; i++) {
                        if (selected == student[i].studentId) {
                            selectedStudent = student[i];
                            console.log(student[i].studentId);
                            //document.getElementById("userName").textContent = student[i].studentId;
                              $('#userName').val(student[i].studentId);
                            tempSelectedStudent = student[i];
                            break;
                        }
                    }
                    if (student != null && selectedStudent != null) {
                        fillStudentDetails(selectedStudent);
                    }
                });
            }
        });

        $("#module").css({"display": "block"});
    });

    $('.viewAttendanceClass').on("click", function () {
        eventFlag = 6;
        document.getElementById("workPlace").style.visibility = "visible";

        if (detachFlag == 0) {
            detachDiv = $("#dataEntry").detach();
            detachFlag = 1;
            //$('#graphBtn').show();
            //$('#tableBtn').show();
            //$('#sort').show();
        }
        if ($('#graphBtn').css("display") == 'none') {
            $('#graphBtn').css({"display": "inline-block"});
            $('#tableBtn').css({"display": "inline-block"});
            $('#sort').css({"display": "inline-block"});
        }
        $("#graphBtn").removeClass("viewSelected");
        $("#tableBtn").removeClass("viewSelected");
        tableView();
    });
    $('.reload').on("click", function () {
        tableView();
    });


}

function fillStaffDetails(lecturer) {

    $('div[id^=I]').each(function () {

        var input = $(this).find("input")[0],
            label = $(this).find("label")[0];
        input = $(input);
        label = $(label);
        if (label.text().trim().match(/^first/i)) {
            input.val(lecturer.firstName);
        }
        if (label.text().toLowerCase().match(/^last/i)) {
            input.val(lecturer.lastName);
        }
        if (label.text().toLowerCase().match(/^email/i)) {
            input.val(lecturer.email);
        }
        if (label.text().toLowerCase().match(/^phone/i)) {
            input.val(lecturer.phoneNumber);
        }
        if (label.text().toLowerCase().match(/^category/i)) {
            var select = $(this).find("select");
            select.val(lecturer.category);
        }
        if (label.text().toLowerCase().match(/^date/i)) {
            var dateOfBirth = lecturer.dateOfBirth;
            var temp = dateOfBirth.split("/");
            if (temp.length < 2) {
                temp = dateOfBirth.split("-");
            }
            dateOfBirth = temp[2] + '-' + temp[1] + '-' + temp[0];
            input.val(dateOfBirth);
        }

    });
}
function fillStudentDetails(student) {

    $('div[id^=I]').each(function () {

        var input = $(this).find("input")[0],
            label = $(this).find("label")[0];
        input = $(input);
        label = $(label);
        if (label.text().trim().match(/^first/i)) {
            input.val(student.firstName);
        }
        if (label.text().toLowerCase().match(/^last/i)) {
            input.val(student.lastName);
        }

        if (label.text().toLowerCase().match(/^gender/i)) {
            var select = $(this).find("select");
            select.val(student.gender);
        }
    });
}
function User() {
    enableInput();
    if (detachFlag == 1) {
        $('#graphBtn').hide();
        $('#tableBtn').hide();
        $('#sort').hide();
        $('#tableStudent').hide();

        $('#staffTable').hide();

        document.getElementById("graph").style.display = "none";
        Reattach();
        detachFlag = 0;
    }
    document.getElementById("module").style.display = "block";
    document.getElementById("studentSelect").style.display = "none";
    var option = '';

    for (var i = 0; i < modulesFromServer.length; i++) {
        option += '<option value="' + modulesFromServer[i] + '">' + modulesFromServer[i] + '</option>';
    }
    $('#selectProgram').append(option);

    //document.getElementById("selectModule").style.display = "none";
    document.getElementById("selectProgram").style.display = "none";
    document.getElementById("input5").style.display = "block";
    document.getElementById("workPlace").style.visibility = "visible";
    $("#lbl1").text("First Name: ");
    $("#lbl2").text("Last Name: ");
    $("#lbl3").text("Phone:");
    $("#lbl4").text("Gender: ");
    var newOptions = {
        "Male": "Male",
        "Female": "Female"
    };

    var $el = $("#inputGender");
    $el.empty(); // remove old options
    $.each(newOptions, function (value, key) {
        $el.append($("<option></option>")
            .attr("value", value).text(key));
    });

    $('#lbl5').text("Email");
    document.getElementById("I6").style.display = "block";
    $("#lbl6").text("Category: ");


    var newOpt = {
        "Admin": "Admin",
        "Lecturer": "Lecturer"
    };

    var $ele = $("#drop");
    $ele.empty(); // remove old options
    $.each(newOpt, function (value, key) {
        $ele.append($("<option></option>")
            .attr("value", value).text(key));
    });

    document.getElementById("I7").style.display = "block";
    $('#lbl7').text("Date of birth");
    document.getElementById("I8").style.display = "none";
    document.getElementById("I9").style.display = "none";
    document.getElementById("I10").style.display = "none";

}
function Attendance() {
    enableInput();

    if (detachFlag == 1) {

        $('#graphBtn').hide();
        $('#tableBtn').hide();
        $('#sort').hide();
        $('#tableStudent').hide();
        $('#staffTable').hide();
        document.getElementById("graph").style.display = "none";
        Reattach();
        detachFlag = 0;
    }
    document.getElementById("module").style.display = "none";
    document.getElementById("workPlace").style.visibility = "visible";
    document.getElementById("studentSelect").style.display = "none";
    //$("#lbl1").text("Student ID: ");
    $("#lbl2").text("First Name: ");
    $("#lbl3").text("Last Name: ");
    $("#lbl4").text("Gender: ");
    var newOptions = {
        "Male": "Male",
        "Female": "Female"
    };

    var $el = $("#inputGender");
    $el.empty(); // remove old options
    $.each(newOptions, function (value, key) {
        $el.append($("<option></option>")
            .attr("value", value).text(key));
    });
    //$("#labelSelectProgram").text("Program: ");
    $("#lbl5").text("Program: ");
    //document.getElementById("selectModule").style.display = "block";
    document.getElementById("selectProgram").style.display = "block";
    document.getElementById("input5").style.display = "none";
    document.getElementById("I1").style.display = "none";
    document.getElementById("I6").style.display = "none";
    document.getElementById("I7").style.display = "none";
    document.getElementById("I8").style.display = "none";
    document.getElementById("I9").style.display = "none";
    document.getElementById("I8").style.display = "none";
}
function tableView() {
    graphFlag = 0;
    $("#workPlace").empty();
    $("#tableBtn").addClass("viewSelected");
    $("#graphBtn").removeClass("viewSelected");
    $('#staffTable').hide();
    $('#tableStudent').show();

    //document.getElementById("graph").style.display = "none";
    $("#graph").hide();
    $('#sort').show();
    if (tableFlag == 0) {
        tableAttachDiv = $("#tableStudent").clone();
        $("#tableStudent").detach();
        tableFlag = 1;
    }
    $("#workPlace").html(tableAttachDiv);
    tableAttachDiv = $("#tableStudent").clone();
    $.ajax({
        url: '/session?sessionOption=module',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            for (var i = 0; i < result.length; i++) {
                var student = result[i].student;
                var dateTaken = result[i].dateTaken;
                var session = result[i].session;
                var status = result[i].status;

                addRow(student, dateTaken, session, status);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
        }

    });


}
function addRow(student, dateTaken, session, status) {

    graphFlag = 0;
    tabBody = document.getElementById("tableTbody");

    var firstName = student.firstName,
        lastName = student.lastName,
        studentID = student.studentId;

    row = document.createElement("tr");

    SnoCell = document.createElement("td");
    firstCell = document.createElement("td");
    lastCell = document.createElement("td");
    dateCell = document.createElement("td");
    sessionCell = document.createElement("td");
    statusCell = document.createElement("td");

    SnoText = document.createTextNode(studentID);
    firstText = document.createTextNode(firstName);
    lastText = document.createTextNode(lastName);
    dateText = document.createTextNode(dateTaken);
    sessionText = document.createTextNode(session);
    statusText = document.createTextNode(status);

    SnoCell.appendChild(SnoText);
    firstCell.appendChild(firstText);
    lastCell.appendChild(lastText);
    dateCell.appendChild(dateText);
    sessionCell.appendChild(sessionText);
    statusCell.appendChild(statusText);

    row.appendChild(SnoCell);
    row.appendChild(firstCell);
    row.appendChild(lastCell);
    row.appendChild(dateCell);
    row.appendChild(sessionCell);
    row.appendChild(statusCell);

    tabBody.appendChild(row);
}


function graphView() {

    var chartData = [];
    graphFlag = 1;
    $.ajax({
        url: '/session?sessionOption=dateTaken',
        type: 'GET',
        dataType: 'json',
        success: function (result) {

            var myData = [];
            var absentCounter = 0;
            for (var i = 0, j = 1; i < result.length; i++, j++) {

                var d, d2;
                d = new Date(result[i].dateTaken);
                var dateStr2;
                var dateStr = d.getDay() + "" + d.getMonth() + "" + d.getFullYear();
                if (result[j] != null) {
                    d2 = new Date(result[j].dateTaken);
                    dateStr2 = d2.getDay() + "" + d2.getMonth() + "" + d2.getFullYear();
                }

                if (result[j] != null && dateStr != dateStr2) {
                    absentCounter = 0;

                } else {
                    absentCounter++;
                }

                //if(absentCounter>0) {
                var date = getStringFormattedDate(result[i].dateTaken);
                var temp = {

                    "Dates": date,
                    "Absences": absentCounter,
                    "Student": result[i].student.firstName + "." + result[i].student.lastName
                };
                //}
                chartData[i] = temp;
            }
            makeGraph(chartData);
        }
    });

}
function getStringFormattedDate(date) {
    date = new Date(date);
    var dateStr;
    if (date != null) {
        dateStr = date.getMonth() + "-" + date.getDay() + "-" + date.getFullYear();

    } else {
        date = new Date();
        dateStr = date.getMonth() + "-" + date.getDay() + "-" + date.getFullYear();
    }
    return dateStr;
}
function makeGraph(chartData) {
    var graphFlag = 1;
    $('#tableStudent').hide();
    //$('#sort').hide();
    document.getElementById("graph").style.display = "block";
    //$("graph").show();
    var chart = AmCharts.makeChart("chart_1", {
        "type": "serial",
        "theme": "light",
        "pathToImages": Metronic.getGlobalPluginsPath() + "amcharts/amcharts/images/",
        "autoMargins": false,
        "marginLeft": 30,
        "marginRight": 8,
        "marginTop": 10,
        "marginBottom": 26,

        "fontFamily": 'Open Sans',
        "color": '#888',

        "dataProvider": chartData,
        "valueAxes": [{
            "axisAlpha": 0,
            "position": "left"
        }],
        "startDuration": 1,
        "graphs": [{
            "alphaField": "alpha",
            "balloonText": "<span style='font-size:13px;'>" +
            "[[Student]]</span>",
            "dashLengthField": "dashLengthColumn",
            "fillAlphas": 1,
            "title": "Absences",
            "type": "column",
            "valueField": "Absences" //Absences
        }, {
            "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
            "bullet": "round",
            "dashLengthField": "dashLengthLine",
            "lineThickness": 3,
            "bulletSize": 7,
            "bulletBorderAlpha": 1,
            "bulletColor": "#FFFFFF",
            "useLineColorForBulletBorder": true,
            "bulletBorderThickness": 3,
            "fillAlphas": 0,
            "lineAlpha": 1,
            "title": "Student",
            "valueField": "Student"
        }],
        "categoryField": "Dates",
        "categoryAxis": {
            "gridPosition": "start",
            "axisAlpha": 0,
            "tickLength": 0
        }
    });

    $('#chart_1').closest('.portlet').find('.fullscreen').click(function () {
        chart.invalidateSize();
    });
}

function submitClick() {
    // eventFlag;
    // 1: addUser 2: modifyUser 3: viewUser
    // 4: addStudentRecord 5: addAttendance 6: viewAttendance
    var validationFlag = 0;
    if (eventFlag == 1 || eventFlag == 2) {
        var firstName = $("#input1").val();
        var lastName = $("#input2").val();
        var phoneNumber = $("#input3").val();
        var gender = $("#inputGender").find(":selected").text();
        var email = $("#input5").val();
        var category = $("#drop").find(":selected").text();
        var dateOfBirth = new Date($("#input7").val());
        if (eventFlag == 1) {
            var username = $("#userName").val();
            var password = $("#userPassword").val();
            password = b64_md5(password);
        }
        var lecturer = JSON.parse(localStorage.getItem("lecturer"));
        if (lecturer != null) {
            if (eventFlag == 2 && lecturer.lecturerId != null) {
                ajaxCalls.user.lecturerId = lecturer.lecturerId;
                ajaxCalls.user.userCredential = {
                    userName: lecturer.userCredential.userName
                };
            }
        }
        if (!validator.validatePhoneNumber(phoneNumber)) {
            validationFlag = 1;
            alert("Please enter valid phone number");
        }
        else if (!validator.validateEmail(email)) {
            validationFlag = 1;
            alert("please enter valid email");
        }
        else {
            validationFlag = 0;
            ajaxCalls.user.firstName = firstName;
            ajaxCalls.user.lastName = lastName;
            ajaxCalls.user.email = email;
            ajaxCalls.user.gender = gender;
            ajaxCalls.user.category = category;
            ajaxCalls.user.phoneNumber = parseInt(phoneNumber);
            ajaxCalls.user.mobileNumber = parseInt(phoneNumber);

            ajaxCalls.user.dateOfBirth = dateOfBirth.getMonth() + "-" + dateOfBirth.getDate() + "-" + dateOfBirth.getFullYear();
            ajaxCalls.user.userCredential = {
                userName: username,
                password: password
            };
        }
    } else if (eventFlag == 4 || eventFlag == 5) {
        //var studentId = $("#input1").val();

        firstName = $("#input2").val();
        lastName = $("#input3").val();
        gender = $("#inputGender").find(":selected").text();
        var i, studentProgram;

        if (eventFlag == 4) {

            studentProgram = $("#selectProgram").find(":selected").text();
            var programId = 0;
            //moduleTitle = $("#selectModule").find("selected").text();
            //ajaxCalls.user.studentID = studentId;
            for (i = 0; i < programsFromServer.length; i++) {
                if (programsFromServer[i].programName == studentProgram) {
                    programId = programsFromServer[i].programId;
                    break;
                }
            }
            ajaxCalls.user.firstName = firstName;
            ajaxCalls.user.lastName = lastName;
            ajaxCalls.user.gender = gender;

            ajaxCalls.user.program = {
                programName: studentProgram,
                programId: programId
            };
        }

        if (eventFlag == 5) {
            var datetake = new Date($("#input7").val());
            var takendate = (datetake.getMonth() + 1) + "-" + (datetake.getDate() + 1) + "-" + datetake.getFullYear() +
                " " + datetake.getHours() + ":" + datetake.getMinutes() + ":" + datetake.getSeconds();
            ajaxCalls.user.dateTaken = takendate;
            //alert( ajaxCalls.user.dateTaken);
            var session = $("#session").find(":selected").text();
            var status = $("#drop").find(":selected").text();
            var selectedStudentId = $("#studentSelect").find(":selected").val();

            ajaxCalls.user.session = session;
            ajaxCalls.user.status = status;
            ajaxCalls.user.studentId = $("#userName").val();
            var module = $("#selectProgram").find(":selected").text(), moduleId = 0;
            //moduleTitle = $("#selectModule").find("selected").text();
            //ajaxCalls.user.studentID = studentId;
            studentProgram = {};
            for (i = 0; i < modulesFromServer != null && modulesFromServer.length; i++) {
                if (modulesFromServer[i].moduleTitle == module) {
                    moduleId = modulesFromServer[i].moduleId;
                    break;
                }
            }
            ajaxCalls.user = {};
            ajaxCalls.user = {
                session: session,
                status: status,
                dateTaken: takendate,
                student: {
                    studentId: selectedStudentId,
                    firstName: firstName,
                    lastName: lastName,
                    gender: gender,
                    program: {
                        programId: tempSelectedStudent.program.programId,
                        programName: tempSelectedStudent.program.programName
                    }
                },
                module: {
                    moduleId: moduleId,
                    moduleTitle: module
                }
            };
            tempSelectedStudent = "";
        }
    }
    switch (eventFlag) {
        case 1:
            if (validationFlag == 0)
                ajaxCalls.ajax1(null, ajaxCalls.user, 2, "/staff");
            //viewAttendance();
            document.alert("New Staff added");
            window.location.href = "/html/home.jsp";
            break;
        case 2:
            if (validationFlag == 0)
                ajaxCalls.ajax1(null, ajaxCalls.user, 3, "/staff");
            window.location.href = "/html/home.jsp";
            //viewAttendance();
            document.alert("Modify added");
            break;
        case 4:
            ajaxCalls.ajax1(null, ajaxCalls.user, 2, "/student");
            window.location.href = "/html/home.jsp";
            //viewAttendance();
            document.alert("Student Record added");
            break;
        case 5:

            ajaxCalls.ajax1(null, ajaxCalls.user, 2, "/session");
            window.location.href = "/html/home.jsp";
            document.alert("Attendance added");
            //viewAttendance();
            break;

    }
}

function sortFunc() {


    var sortBy = $("#sort option:selected").val();

    if (sortBy == "semester") {
        if (graphFlag == 0) {
            $.ajax({
                url: '/session?sessionOption=semester',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    $("#tableTbody").empty();
                    for (var i = 0; i < result.length; i++) {
                        var student = result[i].student;
                        var dateTaken = result[i].dateTaken;
                        var session = result[i].session;
                        var status = result[i].status;

                        addRow(student, dateTaken, session, status);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
                    alert(msg);
                }

            });
        } else if (graphFlag == 1) {

            $.ajax({
                url: '/session?sessionOption=semester',
                type: 'GET',
                dataType: 'json',
                success: function (result) {

                    var myData = [];
                    //console.log(result);
                    var absentCounter = 0;
                    for (var i = 0, j = 1; i < result.length; i++, j++) {


                        if (result[j] != null && result[i].module.semester != result[j].module.semester) {
                            absentCounter = 0;

                        } else {
                            absentCounter++;
                        }

                        //if(absentCounter>0) {
                        var temp = {

                            "Dates": result[i].module.semester,
                            "Absences": absentCounter,
                            "Student": result[i].student.firstName + "." + result[i].student.lastName
                        };
                        //}
                        myData[i] = temp;
                    }

                    makeGraph(myData);
                }
            });

        }

    }

    if (sortBy == "date") {
        if (graphFlag == 0) {

            $.ajax({
                url: '/session?sessionOption=dateTaken',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    $("#tableTbody").empty();
                    for (var i = 0; i < result.length; i++) {
                        var student = result[i].student;
                        var dateTaken = result[i].dateTaken;
                        var session = result[i].session;
                        var status = result[i].status;

                        addRow(student, dateTaken, session, status);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
                    alert(msg);
                }

            });
        } else if (graphFlag == 1) {

            $.ajax({
                url: '/session?sessionOption=dateTaken',
                type: 'GET',
                dataType: 'json',
                success: function (result) {

                    var myData = [];
                    var absentCounter = 0;
                    for (var i = 0, j = 1; i < result.length; i++, j++) {

                        var d, d2;
                        d = new Date(result[i].dateTaken);
                        var dateStr2;
                        var dateStr = d.getDay() + "" + d.getMonth() + "" + d.getFullYear();
                        if (result[j] != null) {
                            d2 = new Date(result[j].dateTaken);
                            dateStr2 = d2.getDay() + "" + d2.getMonth() + "" + d2.getFullYear();
                        }

                        if (result[j] != null && dateStr != dateStr2) {
                            absentCounter = 0;

                        } else {
                            absentCounter++;
                        }

                        //if(absentCounter>0) {
                        var temp = {

                            "Dates": getStringFormattedDate(result[i].dateTaken),
                            "Absences": absentCounter
                        };
                        //}
                        myData[i] = temp;
                    }
                    makeGraph(myData);
                }
            });
        }

    }
    if (sortBy == "module") {
        if (graphFlag == 0) {
            $.ajax({
                url: '/session?sessionOption=module',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    $("#tableTbody").empty();
                    for (var i = 0; i < result.length; i++) {
                        var student = result[i].student;
                        var dateTaken = result[i].dateTaken;
                        var session = result[i].session;
                        var status = result[i].status;

                        addRow(student, dateTaken, session, status);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;

                }

            });
        } else if (graphFlag == 1) {

            $.ajax({
                url: '/session?sessionOption=module',
                type: 'GET',
                dataType: 'json',
                success: function (result) {

                    var myData = [];
                    var absentCounter = 0;
                    for (var i = 0, j = 1; i < result.length; i++, j++) {


                        if (result[j] != null && result[i].module.moduleTitle != result[j].module.moduleTitle) {
                            absentCounter = 0;

                        } else {
                            absentCounter++;
                        }

                        //if(absentCounter>0) {
                        var temp = {

                            "Dates": result[i].module.moduleTitle,
                            "Absences": absentCounter
                        };
                        //}
                        myData[i] = temp;
                    }
                    makeGraph(myData);
                }
            });
        }

    }
    if (sortBy == "absentday") {
        if (graphFlag == 0) {
            var person = prompt("Please Specific date in mm-dd-yyyy format");
            var url;
            if (person != null) {
                url = "/absent?AbsentBySpecificDay=" + person;

                $.ajax({
                    url: url,
                    type: 'get',
                    dataType: 'json',
                    success: function (result) {
                        $("#tableTbody").empty();
                        for (var i = 0; i < result.length; i++) {
                            var student = result[i].student;
                            var dateTaken = result[i].dateTaken;
                            var session = result[i].session;
                            var status = result[i].status;

                            addRow(student, dateTaken, session, status);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
                        alert(msg);
                    }

                });
            }
        } else if (graphFlag == 1) {

            graphFlag = 1;
            var person = prompt("Please Specific date in mm-dd-yyyy format", "mm-dd-yyyy");
            var url;
            var chartData = [];
            if (person != null) {
                url = "/absent?AbsentBySpecificDay=" + person;

                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'json',
                    success: function (result) {

                        var myData = [];
                        var absentCounter = 0;
                        for (var i = 0, j = 1; i < result.length; i++, j++) {

                            var d, d2;
                            d = new Date(result[i].dateTaken);
                            var dateStr2;
                            var dateStr = d.getDay() + "" + d.getMonth() + "" + d.getFullYear();
                            if (result[j] != null) {
                                d2 = new Date(result[j].dateTaken);
                                dateStr2 = d2.getDay() + "" + d2.getMonth() + "" + d2.getFullYear();
                            }

                            if (result[j] != null && dateStr != dateStr2) {
                                absentCounter = 0;

                            } else {
                                absentCounter++;
                            }

                            //if(absentCounter>0) {
                            var date = getStringFormattedDate(result[i].dateTaken);
                            var temp = {

                                "Dates": date,
                                "Absences": absentCounter
                            };
                            //}
                            chartData[i] = temp;
                        }
                        makeGraph(chartData);
                    }
                });
            }
        }

    }
    if (sortBy == "absentmod") {
        if (graphFlag == 0) {
            var person = prompt("Please Specific module");
            var url;
            if (person != null) {
                url = "/absent?AbsentByModule=" + person;

                $.ajax({
                    url: url,
                    type: 'get',
                    dataType: 'json',
                    success: function (result) {
                        $("#tableTbody").empty();
                        for (var i = 0; i < result.length; i++) {
                            var student = result[i].student;
                            var dateTaken = result[i].dateTaken;
                            var session = result[i].session;
                            var status = result[i].status;

                            addRow(student, dateTaken, session, status);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
                        alert(msg);
                    }

                });
            }
        } else if (graphFlag == 1) {

            var chartData = [];
            graphFlag = 1;
            var person = prompt("Please Specific module");
            var url;
            if (person != null) {
                url = "/absent?AbsentByModule=" + person;

                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'json',
                    success: function (result) {

                        var myData = [];
                        var absentCounter = 0;
                        for (var i = 0, j = 1; i < result.length; i++, j++) {

                            var d, d2;
                            d = new Date(result[i].dateTaken);
                            var dateStr2;
                            var dateStr = d.getDay() + "" + d.getMonth() + "" + d.getFullYear();
                            if (result[j] != null) {
                                d2 = new Date(result[j].dateTaken);
                                dateStr2 = d2.getDay() + "" + d2.getMonth() + "" + d2.getFullYear();
                            }

                            if (result[j] != null && dateStr != dateStr2) {
                                absentCounter = 0;

                            } else {
                                absentCounter++;
                            }

                            //if(absentCounter>0) {
                            var date = getStringFormattedDate(result[i].dateTaken);
                            var temp = {

                                "Dates": date,
                                "Absences": absentCounter
                            };
                            //}
                            chartData[i] = temp;
                        }
                        makeGraph(chartData);
                    }
                });
            }
        }
    }
    if (sortBy == "absentweek") {
        if (graphFlag == 0) {
            var person = prompt("Please Specific week number");
            var url;
            if (person != null) {
                url = "/absent?AbsentByWeek=" + person;

                $.ajax({
                    url: url,
                    type: 'get',
                    dataType: 'json',
                    success: function (result) {
                        $("#tableTbody").empty();
                        for (var i = 0; i < result.length; i++) {
                            var student = result[i].student;
                            var dateTaken = result[i].dateTaken;
                            var session = result[i].session;
                            var status = result[i].status;

                            addRow(student, dateTaken, session, status);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
                        alert(msg);
                    }

                });
            }
        } else if (graphFlag == 1) {

            var chartData = [];
            graphFlag = 1;
            var person = prompt("Please Specific week number");
            var url;
            if (person != null) {
                url = "/absent?AbsentByWeek=" + person;

                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'json',
                    success: function (result) {

                        var myData = [];
                        var absentCounter = 0;
                        for (var i = 0, j = 1; i < result.length; i++, j++) {

                            var d, d2;
                            d = new Date(result[i].dateTaken);
                            var dateStr2;
                            var dateStr = d.getDay() + "" + d.getMonth() + "" + d.getFullYear();
                            if (result[j] != null) {
                                d2 = new Date(result[j].dateTaken);
                                dateStr2 = d2.getDay() + "" + d2.getMonth() + "" + d2.getFullYear();
                            }

                            if (result[j] != null && dateStr != dateStr2) {
                                absentCounter = 0;

                            } else {
                                absentCounter++;
                            }

                            //if(absentCounter>0) {
                            var date = getStringFormattedDate(result[i].dateTaken);
                            var temp = {

                                "Dates": date,
                                "Absences": absentCounter
                            };
                            //}
                            chartData[i] = temp;
                        }
                        makeGraph(chartData);
                    }
                });
            }
        }

    }

}
var inputs = [], oldEdit, oldCancel;

var viewAllStaff = {

    getNDisplayAllStaff: function () {
        var me = this;
        $.ajax({
            type: "GET",
            url: "/staff",
            dataType: "json",
            success: function (jsonData, response) {
                var dynamicStaffTableHtml = "<tbody id='viewStaffTableBody'>", i = 0;
                if (jsonData != null && jsonData.length > 0) {
                    for (i = 0; i < jsonData.length; i++) {
                        dynamicStaffTableHtml += "<tr>";
                        dynamicStaffTableHtml +=
                            '<td id="td' + jsonData[i].lecturerId + '">' +
                                //'<td>' +
                            '<input  type="text" name="firstName" class="form-control input-small" disabled="true"' +
                            ' value="' +
                            jsonData[i].firstName + '"/></td>"';
                        dynamicStaffTableHtml +=
                            '<td>'
                            +
                            '<input disabled="true" type="text" name="lastName" class="form-control input-small" value="' +
                            jsonData[i].lastName + '"/></td>"';
                        dynamicStaffTableHtml +=
                            '<td >' +
                            '<input disabled="true" type="text" name="email" class="form-control input-small" value="' +
                            jsonData[i].email + '"/></td>"';
                        dynamicStaffTableHtml +=
                            '<td >' +
                            '<input disabled="true" type="date" name="dateOfBirth" class="form-control input-small"' +
                            ' value="' +
                            me.getDateForDateInputField(jsonData[i].dateOfBirth) + '"/></td>"';
                        dynamicStaffTableHtml +=
                            '<td >' +
                            '<input disabled="true" type="text" name="category" class="form-control input-small" value="' +
                            jsonData[i].category + '"/></td>"';
                        dynamicStaffTableHtml +=
                            '<td >' +
                            '<input disabled="true" type="text" name="gender" class="form-control input-small" value="' +
                            jsonData[i].gender + '"/></td>"';
                        dynamicStaffTableHtml +=
                            '<td >' +
                            '<input disabled="true" type="text" name="phoneNumber" class="form-control input-small" value="' +
                            jsonData[i].phoneNumber + '"/></td>"';
                        dynamicStaffTableHtml += "<td><a class='edit' href='javascript:;'>  Edit </a></td>";
                        dynamicStaffTableHtml += "<td><a class='delete' href='javascript:;'> Delete </a></td>";
                        dynamicStaffTableHtml += "</tr>";
                    }
                }
                dynamicStaffTableHtml += "</tbody>";
                $("#editableStaffTable").append(dynamicStaffTableHtml);
                me.enableEventsForAllStaff();
            }
        });
    },
    handleCancelAllStaff: function (cancelTag) {

        //var cancelId = $(cancelTag);
        //if (cancelId == null || cancelId.text() !== 'cancel') {
        //    return false;
        //}

        if (inputs.length > 1) {
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].attr("disabled", "true");
            }
            if (oldEdit != null) oldEdit.val("Edit").text("Edit").removeClass("save").addClass("edit");

            if (oldCancel != null) oldCancel.val("Delete").text("Delete").removeClass("cancel").addClass("delete");
        }
        inputs = [];
        oldEdit = null;
        oldCancel = null;
    },

    handleEditAllStaff: function (editTag) {

        var saveId = $(editTag), me = this;
        if (saveId == null || saveId.text().toLowerCase() == 'save') {
            me.handleSaveAllStaff(editTag);
            return false;
        }

        if (inputs.length > 1) {
            me.handleCancelAllStaff();
        }
        var parent = $(editTag).parent();
        if (parent != null) {
            $(parent).siblings("td").each(function () {
                $(this).find("input").each(function () {
                    inputs.push($(this));
                    $(this).removeAttr("disabled");
                });
                oldCancel = $(this).find("a.delete");
                if (oldCancel != null && oldCancel.length == 1) {
                    oldCancel = $(oldCancel[0]).val("Cancel").text("Cancel").removeClass("delete").addClass("cancel");
                }
            });

        }
        oldEdit = $(editTag).val("Save").text("Save").removeClass("edit").addClass("save");

    },

    handleSaveAllStaff: function (saveTag) {

        //var saveId = $("a.save");
        //if (saveId == null || saveId.text().toLowerCase() !== 'save') {
        //    return false;
        //}
        var confirmDialog = confirm("Confirm to Save to server. Click Enter to continue");
        if (confirmDialog == null || confirmDialog == false) {
            return;
        }
        var parent = $(saveTag).parent(), lecturerIdToDelete = -1, values = [], properties = [], me = this;
        $(parent).siblings("td").each(function () {
            if ($(this).attr("id") != null) {
                lecturerIdToDelete = $(this).attr("id");
            }
            $(this).find("input").each(function () {

                values.push($(this).val());
                properties.push($(this).attr("name"));
                $(this).removeAttr("disabled");
            });

        });
        var data = {};
        data.userCredential = {};
        for (var i = 0; i < properties.length; i++) {
            if (properties[i] == "userName") {
                data.userCredential[properties[i]] = values[i];
            } else {
                if (properties[i].trim() == 'category') {
                    values[i] = "lecturer";

                    if (values[i].trim().toLowerCase() != "admin" && values[i].trim().toLowerCase() != "lecturer") {
                        alert("Category can only be Admin or Lecturer");
                        return;
                    }
                }
                if (properties[i] == 'gender') {

                    if (values[i].toLowerCase() != "male" && values[i].toLowerCase() != "female") {
                        alert("Gender can only be Male or Female");
                        return;
                    }
                }
                if (properties[i] == 'dateOfBirth') {
                    values[i] = me.getServerFormattedDate(values[i]);
                }
                data[properties[i]] = values[i];
            }
        }
        data.mobileNumber = data.phoneNumber;
        if (lecturerIdToDelete != null) {
            data.lecturerId = lecturerIdToDelete.replace("td", "");
        }
        data = JSON.stringify(data);

        $.ajax({
            type: "PUT",
            url: "/staff",
            dataType: "json",
            data: data,
            success: function (jsonData, response) {
                if (jsonData != null) {
                    $("#viewStaffTableBody").remove();
                    me.getNDisplayAllStaff();
                }
            }
        });
    },

    handleDeleteAllStaff: function (deleteTag) {

        var cancelId = $(deleteTag), me = this;
        if (cancelId == null || cancelId.text().toLowerCase() == 'cancel') {
            me.handleCancelAllStaff(deleteTag);
            return false;
        }
        var parent = $(deleteTag).parent(), lecturerIdToDelete = -1; //values = [], properties = [];
        if (parent == null) return;
        $(parent).siblings("td").each(function () {
            if ($(this).attr("id") != null) {
                lecturerIdToDelete = $(this).attr("id");
            }
        });
        var temp = confirm("Press Ok to Delete");
        if (temp != null && temp == true) {


            //return false;
            $.ajax({
                type: "Delete",
                url: "/staff?lecturer=" + lecturerIdToDelete,
                dataType: "json",
                success: function (jsonData, response) {
                    if (jsonData != null) {

                    }
                    $("#viewStaffTableBody").remove();
                    me.getNDisplayAllStaff();
                }
            });
        }
    },

    enableEventsForAllStaff: function () {
        var me = this;
        $("a.edit").on("click", function () {
            me.handleEditAllStaff(this);
        });
        //$("a.save").on("click", function () {
        //    me.handleSaveAllStaff(this);
        //});
        $("a.delete").on("click", function () {
            me.handleDeleteAllStaff(this);
        });
        //$("a.cancel").on("click", function () {
        //    me.handleCancelAllStaff(this);
        //});
    },
    getServerFormattedDate: function (date) {

        if (date != null) {
            var temp = date.split("/");
            if (temp.length < 2) {
                temp = date.split("-");
            }
            return temp[2] + '-' + temp[1] + '-' + temp[0];
        } else return null;
    },
    getDateForDateInputField: function (date) {

        if (date == null || date === '') {
            date = new Date();
            return date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);
        } else {
            var temp = date.split("/");
            if (temp.length < 2) {
                temp = date.split("-");
            }
            temp[1] = temp[1]< 10 && temp[1].length < 2? '0'+ temp[1]: temp[1];
            temp[0] = temp[0]< 10 && temp[0].length < 2? '0'+ temp[0]: temp[0];
            return temp[2] + '-' + temp[1] + '-' + temp[0];
        }
    }
};