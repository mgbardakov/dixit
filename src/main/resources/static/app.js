var stompClient = null;
var lobbySubscription = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket?userName=' + $("#user-name").val());
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/'+ frame.headers['user-name'] +'/queue/private-messages', function (message) {
            showPrivateMessage(JSON.stringify(message));
        }, {'customHeader': "someInfo"});
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function createLobby() {
    stompClient.send('/app/lobby', {}, JSON.stringify({"type": "CREATE_LOBBY"}))
}

function joinLobby() {
    lobbySubscription = stompClient.subscribe('/topic/lobby/' + $("#lobby-key").val(), function (message) {
        showLobbyMessage(message)
    })
}

function leaveLobby() {
    lobbySubscription.unsubscribe();
}

function showPrivateMessage(message) {
    let jsonMessage = JSON.parse(message);
    let payload = JSON.parse(jsonMessage.body).payload
    $("#private-messages").append("<tr><td>" + JSON.stringify(payload) + "</td></tr>");
}
function showLobbyMessage(message) {
    let payload = JSON.parse(message.body).payload
    console.log(payload)
    $("#lobby-message").append("<tr><td>" + JSON.stringify(payload) + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#create" ).click(function() { createLobby(); });
    $( "#join" ).click(function() { joinLobby(); });
    $( "#leave" ).click(function() { leaveLobby(); });
});
