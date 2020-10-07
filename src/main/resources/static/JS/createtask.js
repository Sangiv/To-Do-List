document.querySelector("form.createTaskForm").addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.createTaskForm").elements;
     console.log(formElements);
    let name=formElements["inputName"].value
    let tasklistId=formElements["inputId"].value

    createTaskList(name,tasklistId)
  });

  function createTaskList(name, tasklistId){
    fetch("http://localhost:8901/task/create/", {
        method: 'post',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "name": name,
            "tasklist": {
                "id": tasklistId
            }
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