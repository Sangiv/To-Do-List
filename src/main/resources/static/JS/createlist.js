document.querySelector("form.createListForm").addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.createListForm").elements;
     console.log(formElements);
    let name=formElements["inputName"].value

    createTaskList(name)
  });

  function createTaskList(name){
    fetch("http://localhost:8901/tasklist/create/", {
        method: 'post',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "name": name,
        })
      })
      .then(json)
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
  }