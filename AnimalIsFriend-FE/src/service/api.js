import axios from "./axios";

async function signUp(form, accessToken) {
    alert(axios);
    const option = {
      headers: {
          Authorization: "Bearer " + accessToken,
      },
    };
    const res = await axios.post("/user/signup", form, option);
    const result = res.data;

    return result;
}

export { signUp }