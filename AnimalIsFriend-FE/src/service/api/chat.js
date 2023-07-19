import axios from "../axios";
function errorResponse(errorCode, errorMessage) {
  if(errorCode == "403" || errorMessage === "유효하지 않은 토큰입니다.") {
    alert("로그인이 필요한 서비스 입니다.");
  } else if(errorMessage) {
    alert(errorMessage);
  }
}

async function checkChatRoom(form, accessToken) {
  try{
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.post("/chat/validation", form, option);
    return res;
  } catch (err) {
    errorResponse(null, err.response.data.message);
  }
}

async function createChatRoom(form, accessToken) {
  try{
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.post("/chat", form, option);
    return res;
  } catch (err) {
    console.log(err);
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

async function getChatRoom(accessToken) {
  try{
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.get("/chat/chatroom?chatroomId=null", option);
    return res;
  } catch (err) {
    console.log(err);
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

async function getChat(accessToken, chatroomId, date) {
  try{
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.get(
      `/chat/${chatroomId}?chatId=&requestDate=${date}`,
      option,
      {}
    );
    return res;
  } catch (err) {
    console.log(err);
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

async function readChat(accessToken, chatroomId, readChat) {
  try{
    if(chatroomId === undefined) {
      chatroomId = null;
    }
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.get(
      `/chat/readChat?chatroomId=${chatroomId}&readChat=${readChat}`,
      option
    );
    return res;
  } catch (err) {
    console.log(err);
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

export { createChatRoom, checkChatRoom, getChatRoom, getChat, readChat }