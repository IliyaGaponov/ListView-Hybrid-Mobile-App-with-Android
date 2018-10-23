var mainObj = {};

mainObj.addItem = function(){
    var itemName = document.newTaskForm.taskName.value;
    var lastDate = document.newTaskForm.lastDate.value;
    var request = new XMLHttpRequest();
    var boolIfCompleted = false;

    if(itemName != "")
    {
        request.open("GET",
            "http://192.168.43.245:8080/ftask/rest/user/add?name="
            + itemName + "&date=" + lastDate + "&username=" + mainObj.currentUser, true);
        request.onreadystatechange = function () {
            if(request.readyState == 4 && request.status == 200)
            {
                boolIfCompleted = JSON.parse(request.responseText);
                if(boolIfCompleted == true)
                {
                    mainObj.cleanIncorrectMessage('newtaskmsg');
                    mainObj.getMainPage();
                }
            }
        }

        request.send(null);
    }
    else
    {
        mainObj.setIncorrectMessage('newtaskmsg','Enter task at first');
    }

}

mainObj.deleteItem = function(itemId){
    var boolIfCompleted = false;
    var request = new XMLHttpRequest();

    request.open("GET", "http://192.168.43.245:8080/ftask/rest/user/delete?id=" + itemId, true);
    request.onreadystatechange = function () {
        if(request.readyState == 4 && request.status == 200)
        {
            boolIfCompleted = JSON.parse(request.responseText);
            if(boolIfCompleted == true)
            {
                mainObj.getMainPage();
            }
        }
    }

    request.send(null);
}

mainObj.updateItem = function(itemId, itemName, lastDate){
    var boolIfCompleted = false;
    var request = new XMLHttpRequest();

    request.open("GET", "http://192.168.43.245:8080/ftask/rest/user/update?id="
        + itemId + "&name=" + itemName + "&date=" + lastDate, true);
    request.onreadystatechange = function () {
        if(request.readyState == 4 && request.status == 200)
        {
            boolIfCompleted = JSON.parse(request.responseText);
            if(boolIfCompleted == true)
            {
                mainObj.getMainPage();
            }
        }
    }

    request.send(null);
}

mainObj.toUpdateItemPage = function(itemId, itemName, lastDate){
    document.editTaskForm.taskName.value = itemName;
    document.editTaskForm.lastDate.value = lastDate;
    document.getElementById('applyUpdate').onclick =  function () {
        if(document.editTaskForm.taskName.value != "")
        {
            mainObj.cleanIncorrectMessage('edittaskmsg');
            mainObj.updateItem(itemId, document.editTaskForm.taskName.value, document.editTaskForm.lastDate.value);
        }
        else
        {
            mainObj.setIncorrectMessage('edittaskmsg','Enter task at first');
        }
    };
}

mainObj.getMainPage = function()
{
    var request = new XMLHttpRequest();

    document.location.href = '#mainpage';
    request.open("GET",
        "http://192.168.43.245:8080/ftask/rest/user/itemslist?username=" + mainObj.currentUser, true);
    request.onreadystatechange = function()
    {
        if(request.readyState == 4 && request.status == 200)
        {
            var list = document.getElementById('itemsList');
            $('#itemsList').empty();
            var itemsArr = JSON.parse(request.responseText);
            if(itemsArr.length != 0)
            {
                 mainObj.cleanIncorrectMessage('emptylistmsg');
            }
            else
            {
                mainObj.setIncorrectMessage('emptylistmsg',
                '<br/><br/><br/><br/><br/>- Click \'+\' to add the 1st task -');
            }

            itemsArr.forEach(
              function (ob)
              {
                  var itemName = document.createTextNode(ob.itemName);
                  var lastDate = document.createTextNode("Due date: " + ob.lastDate);
                  var deleteText = document.createTextNode("Delete");
                  var updateText = document.createTextNode("Update");
                  var li = document.createElement("li");
                  var div = document.createElement("div");
                  var h4 = document.createElement("h4");
                  var smallName = document.createElement("small");
                  var p = document.createElement("p");
                  var br = document.createElement("br");
                  var aDelete = document.createElement("a");
                  var smallDelete = document.createElement("small");
                  var aUpdate = document.createElement("a");
                  var smallUpdate = document.createElement("small");

                  p.setAttribute("style","color: cornflowerblue");
                  aDelete.setAttribute("data-role", "button");
                  aDelete.setAttribute("data-inline", "true");
                  aDelete.setAttribute("data-icon", "delete");
                  aDelete.setAttribute("data-mini", "true");
                  aDelete.setAttribute("onclick", "mainObj.deleteItem(" + ob.itemId + ")");
                  aUpdate.setAttribute("data-role", "button");
                  aUpdate.setAttribute("data-inline", "true");
                  aUpdate.setAttribute("data-icon", "edit");
                  aUpdate.setAttribute("data-mini", "true");
                  aUpdate.setAttribute("href", "#updateitempage");
                  aUpdate.setAttribute("onclick",
                      "mainObj.toUpdateItemPage(" + ob.itemId + ",\'" + ob.itemName + "\',\'" + ob.lastDate + "\')");
                  div.setAttribute("data-role", "collapsible");
                  div.setAttribute("data-collapsed-icon", "arrow-d");
                  div.setAttribute("data-expanded-icon", "arrow-u");
                  smallName.appendChild(itemName);
                  h4.appendChild(smallName);
                  if(ob.lastDate != "")
                  {
                      p.appendChild(lastDate);
                  }

                  smallDelete.appendChild(deleteText);
                  smallUpdate.appendChild(updateText);
                  aDelete.appendChild(smallDelete);
                  aUpdate.appendChild(smallUpdate);
                  div.appendChild(h4);
                  div.appendChild(p);
                  div.appendChild(br);
                  div.appendChild(aDelete);
                  div.appendChild(aUpdate);
                  li.appendChild(div);
                  list.appendChild(li);
              }
            );

            $('#itemsList').listview().listview('refresh');
            $("#mainpage").trigger("create");
        }

    }

    request.send(null);
    document.newTaskForm.taskName.value = "";
    document.newTaskForm.lastDate.value = "";
    document.getElementById('usermenu').innerHTML = mainObj.currentUser;
}

mainObj.login = function(username, password){
    var boolIfMatches = false;
    var request = new XMLHttpRequest();
    request.open("POST",
        "http://192.168.43.245:8080/ftask/rest/user/login?username=" + username + "&password=" + password, true);
    request.onreadystatechange = function () {
        if(request.readyState == 4 && request.status == 200)
        {
            boolIfMatches = JSON.parse(request.responseText);
            if(boolIfMatches == false)
            {
                mainObj.setIncorrectMessage('incorrectInputMsg','Incorrect username and/or password');
            }
            else
            {
                mainObj.currentUser = username;
                mainObj.getMainPage();
            }
        }
    };

    request.send(null);
}

mainObj.logout = function () {
    mainObj.currentUser = null;
    document.getElementById('logname').value = "";
    document.getElementById('logpassword').value = "";
    document.getElementById('usernamereg').value = "";
    document.getElementById('passwordreg').value = "";
    document.getElementById('password2reg').value = "";
    mainObj.cleanIncorrectMessage('incorrectusername');
    mainObj.cleanIncorrectMessage('passwordmsg');
    mainObj.cleanIncorrectMessage('password2msg');
    $('#itemsList').empty();
    document.location.href = '#login';
}

mainObj.register = function(username, password, password2){
    var validUsername = false;
    var validPassword = false;
    var validPassword2 = false;

    if(username == "" || document.getElementById('usernameExists').innerText != "")
    {
        mainObj.setIncorrectMessage('incorrectusername','Incorrect input');
        validUsername = false;
    }
    else
    {
        mainObj.cleanIncorrectMessage('incorrectusername');
        validUsername = true;
    }

    if(password == "")
    {
        mainObj.setIncorrectMessage('passwordmsg','Incorrect input');
        validPassword = false;
    }
    else
    {
        mainObj.cleanIncorrectMessage('passwordmsg');
        validPassword = true;
    }

    if(password2 == "")
    {
        mainObj.setIncorrectMessage('password2msg','Incorrect input');
        validPassword2 = false;
    }
    else if(password2 != password)
    {
        mainObj.setIncorrectMessage('password2msg','The password confirmation does not match');
    }
    else
    {
        mainObj.cleanIncorrectMessage('password2msg');
        validPassword2 = true;
    }

    if(validUsername == true && validPassword == true && validPassword2 == true)
    {
        var boolIfCompleted = false;
        var request = new XMLHttpRequest();

        request.open("POST",
            "http://192.168.43.245:8080/ftask/rest/user/registration?username="
            + username + "&password=" + password, true);
        request.onreadystatechange = function () {
            if(request.readyState == 4 && request.status == 200)
            {
                boolIfCompleted = JSON.parse(request.responseText);
                if(boolIfCompleted == true)
                {
                    mainObj.login(username, password);
                }
            }
        };

        request.send(null);
    }
}

mainObj.checkIfUserExists = function(username){
    var request = new XMLHttpRequest();

    request.open("GET",
        "http://192.168.43.245:8080/ftask/rest/user/checkuser?username=" + username,true);
    request.onreadystatechange = function () {
        if(request.readyState == 4 && request.status == 200)
        {
            var exists = JSON.parse(request.responseText);
            if(exists == true)
            {
                mainObj.setIncorrectMessage('usernameExists','Username already exists');
            }
            else
            {
                mainObj.cleanIncorrectMessage('usernameExists');
                mainObj.cleanIncorrectMessage('incorrectusername');
            }
        }
    };

    request.send(null);
}

mainObj.setIncorrectMessage = function(msgId, msg){
    document.getElementById(msgId).innerHTML = msg;
}

mainObj.cleanIncorrectMessage = function(msgId){
    document.getElementById(msgId).innerHTML = "";
}



