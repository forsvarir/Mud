var stompClient = null;
var connected = false;
var count = 0;

function connect(command) {
    var socket = new SockJS('/mud');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        connected = true;
        console.log('Connected: ' + frame);
        stompClient.subscribe('/ws/responses', function (response) {
            showResponse(JSON.parse(response.body).response);
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

