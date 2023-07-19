import axios from "../axios";
function errorResponse(errorCode, errorMessage) {
  if(errorCode == "403" || errorMessage === "유효하지 않은 토큰입니다.") {
    alert("로그인이 필요한 서비스 입니다.");
  } else if(errorMessage) {
    alert(errorMessage);
  }
}

async function adoptSuccess(form, accessToken) {
  try{
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.post("/adopt", form, option);
    const result = res.data;
    alert(result);
    return result;
  } catch (err) {
    errorResponse(err.response.data.status, err.response.data.message);
  }
}

export { adoptSuccess }