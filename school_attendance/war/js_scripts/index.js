var ajaxCalls = {
    count: 0,
    user: {},
    ajax1: function (btn, data, requestType, url) {
        var me = ajaxCalls;
        if (btn != null) {
            btn.off("click");
        }
        switch (requestType) {

            case 1:
            {
                me.type = "GET";
                break;
            }
            case 2:
            {
                me.type = "POST"; // Create
                break;
            }
            case 3:
            {
                me.type = "PUT"; // Update , sometimes POST is use as both update and create
                break;
            }
            case 4:
            {
                me.type = "DELETE";
                break;
            }
        }

        //var temp = window.btoa(data.user.userCredentials.password);
        //window.atob(temp);

        data = JSON.stringify(data);
        console.log(data, url, me.type);
        $.ajax({
            url: url,
            type: me.type,
            dataType: 'json',
            data: data,
            success: function (result) {
                if (me.type == 1) {

                    $("#input1").text(result[0].firstName);
                    $("#input2").text(result[1].lastName);
                    $("#input3").text(result[2].mobileNumber);
                    $(function () {
                        $('[name=inputGender] option').filter(function () {
                            return ($(this).text() == result[3].gender); //To select
                        }).prop('selected', true);
                    });
                    $("#input5").text(result[4].email);
                    $(function () {
                        $('[name=drop] option').filter(function () {
                            return ($(this).text() == result[5].category); //To select
                        }).prop('selected', true);
                    });
                    $("#input7").text(result[6].dateOfBirth);


                }
                else if (me.type == 2)
                    alert("Creation successful");
                else if (me.type == 3)
                    alert("update successful");
                else if (me.type == 4)
                    alert("deletion successful");

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var msg = "Error: \n" + "XMLHttpRequest: " + XMLHttpRequest + "\n textStatus: " + textStatus + "\n errorThrown: " + errorThrown;
                alert(msg);
            },
            complete: function (data) {

            }
        });
    }
};

var validator = {
    validateEmail: function (email) {

        var emailRegex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
        //return emailRegex.test(email);
        return email.match(emailRegex);

    },
    validatePhoneNumber: function (number) {

        var numberRegex = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
        //return numberRegex.test(number);
        return number.match(numberRegex);
    }
};
