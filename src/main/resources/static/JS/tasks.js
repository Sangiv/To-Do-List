const params = new URLSearchParams(window.location.search);
    
for(const param of params){
  console.log(param);
  let id = param[1];
  console.log(id);
  readOne(id);
}

function readOne(id){
    fetch('http://localhost:8901/task/readOne/'+id)
    .then(
      function(response) {
        if (response.status !== 200) {
          console.log('Looks like there was a problem. Status Code: ' +
            response.status);
          return;
        }
  
        // Examine the text in the response
        response.json().then(function(taskRecord) {
          console.log(taskRecord);
          document.getElementById("inputID").value=taskRecord.id;
          document.getElementById("inputName").value=taskRecord.name;
          getTasks(taskRecord.tasks);

        });
      }
    )
    .catch(function(err) {
      console.log('Fetch Error :-S', err);
    });
}

document.querySelector("form.taskForm").addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.taskForm").elements;
     console.log(formElements);
    let id=formElements["inputID"].value;
    let name=formElements["inputName"].value

    updateTask(id,name)
  });

  function updateTask(id,name){
    fetch("http://localhost:8901/task/update/"+id, {
        method: 'put',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "id": id,
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

  function getTasks(tasks){
      console.log(tasks);
      for(let key of tasks) {
          console.log(key);
          for(item in key) {
              console.log(key[item])

          }
      }
  }