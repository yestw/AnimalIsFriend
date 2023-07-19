import axios from "../axios";
function errorResponse(errorCode, errorMessage) {
  if(errorCode == "403") {
    alert("로그인이 필요한 서비스 입니다.");
  }
  if(errorMessage === "유효하지 않은 토큰입니다.") {
    alert("로그인이 필요한 서비스입니다.")
  }
}


async function signUp(form, accessToken) {
  try {
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.post("/user/signup", form, option);

    return res;
  } catch (err) {
    errorResponse(err.response.data.status, null);
  }
}

export { signUp }