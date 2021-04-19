var stompClient = null;
var connected = false;
var count = 0;
var sessionId = "";

function getSessionId(stompUrl) {
    return stompUrl.replace("ws://localhost:8080/mud/",  "")
                    .replace("/websocket", "")
                    .replace(/^[0-9]+\//, "");
}

function connect() {
    var socket = new SockJS('/mud');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        connected = true;
        console.log('Connected: ' + frame);

        sessionId = getSessionId(stompClient.ws._transport.url);

        stompClient.subscribe('/ws/responses', function (response) {
            console.log(response);
            showResponse(JSON.parse(response.body).response);
        });
        stompClient.subscribe('/user/queue/session', function(response) {
            console.log('Session: ' + JSON.parse(response.body).status)
            $("#player").css('background-color', '#66FF00');
            $('#connect_form').hide();
            $('#send_form').show(0, function() {
                $("#command").focus();
            });
        });
        stompClient.subscribe('/user/queue/response', function(response) {
            decodedMessage = JSON.parse(response.body);
            if(decodedMessage.sessionId == sessionId) {
                showResponse(decodedMessage.response);
            } else {
                console.log("ignored message!");
            }
        });
        stompClient.send("/mud/createSession", {}, JSON.stringify({'playerName': $("#player").val(), 'playerPassword': $('#player_password').val()}));
    });
}

function sendCommand() {
    var command = $("#command").val()
    $("#command").val("");

    if(!connected) {
        console.log("Not connected!");
    } else {
        stompClient.send("/mud/command", {}, JSON.stringify({'command': command}));
    }
}

function connectCommand() {
    if(!connected) {
        connect();
    } else {
        console.log("Already connected!");
    }

}

function showResponse(output) {
    if(count >= 10) {
        $('#responses tr:first').remove();
    } else {
        count++
    }
    $("#responses").append("<tr><td>" + output + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendCommand(); });
    $( "#connect" ).click(function() { connectCommand(); });
    $("#player").focus();
});

