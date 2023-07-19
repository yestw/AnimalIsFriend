import axios from "../axios";
function errorResponse(errorCode, errorMessage) {
  if(errorCode == "403" || errorMessage === "유효하지 않은 토큰입니다.") {
    alert("로그인이 필요한 서비스 입니다.");
  } else if(errorMessage) {
    alert(errorMessage);
  }
}

async function register(form, accessToken) {
  try{
    alert(axios);
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.post("/pet", form, option);
    const result = res.data;
    alert(result);
    return result;
  } catch (err) {
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

async function petView() {
  try {
    const res = await axios.get("/pet");
    return res;
  } catch (err) {
    console.log(err);
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

async function petDetail(pid) {
  try {
    const res = await axios.get(`/pet/${pid}`)
    return res;
  } catch (err) {
    console.log(err);
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

async function petStatusUpdate(form, accessToken) {
  try{
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.patch("/pet", form, option);
    const result = res.data;
    alert(result);
    return result;
  } catch (err) {
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

export { register, petView, petDetail, petStatusUpdate }