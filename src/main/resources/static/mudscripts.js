var stompClient = null;
var connected = false;
var count = 0;
var sessionId = "";

function getSessionId(stompUrl) {
    return stompUrl.replace("ws://localhost:8080/mud/",  "")
                    .replace("/websocket", "")
                    .replace(/^[0-9]+\//, "");
}

function connect(command) {
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
        stompClient.subscribe('/user/queue/response', function(response) {
            console.log("private:" + response);
            decodedMessage = JSON.parse(response.body);
            if(decodedMessage.sessionId == sessionId) {
                showResponse(decodedMessage.response);
            } else {
                console.log("ignored message!");
            }
        });
        stompClient.send("/mud/command", {}, JSON.stringify({'command': command}));
    });
}

function sendCommand() {
    var command = $("#command").val()
    $("#command").val("");

    if(!connected) {
        connect(command);
    } else {
        stompClient.send("/mud/command", {}, JSON.stringify({'command': command}));
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
});

