import { HOST_URL } from "../const";

const getDefaultOptions = (values) => ({
  mode: "cors",
  method: "POST",
  credentials: "include",
  headers: {
    Accept: "application/json, text/plain, */*",
    "Content-Type": "application/json;charset=UTF-8",
  },
  body: JSON.stringify(values),
});

export const fetchApi = async ({
  values,
  api,
  searchParams,
  formData,
  options,
}) => {
  const url = new URL(`${HOST_URL}/${api}`);

  if (searchParams) {
    Object.keys(searchParams).forEach((key) =>
      url.searchParams.append(key, searchParams[key])
    );
  }

  if (formData) {
    let formData = new FormData();
    Object.keys(values).forEach((key) => formData.append(key, values[key]));
  }
  const defaultOptions = getDefaultOptions(values);
  console.log(defaultOptions);
  const mergedOptions = {
    ...defaultOptions,
    ...options,
  };

  const response = await fetch(url, mergedOptions);
  return response;
};
