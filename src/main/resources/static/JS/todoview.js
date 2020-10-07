const params = new URLSearchParams(window.location.search);
    
for(const param of params){
  console.log(param);
  let id = param[1];
  console.log(id);
  readOne(id);
}

function readOne(id){
    fetch('http://localhost:8901/tasklist/readOne/'+id)
    .then(
      function(response) {
        if (response.status !== 200) {
          console.log('Looks like there was a problem. Status Code: ' +
            response.status);
          return;
        }
  
        // Examine the text in the response
        response.json().then(function(todoRecord) {
          console.log(todoRecord);
          document.getElementById("inputID").value=todoRecord.id;
          document.getElementById("inputName").value=todoRecord.name;

          let table = document.querySelector("table");
          createTableBody(table, todoRecord.tasks);

        });
      }
    )
    .catch(function(err) {
      console.log('Fetch Error :-S', err);
    });
}

document.querySelector("form.todoForm").addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.todoForm").elements;
     console.log(formElements);
    let id=formElements["inputID"].value;
    let name=formElements["inputName"].value

    updatetodo(id,name)
  });

  function updatetodo(id,name){
    fetch("http://localhost:8901/tasklist/update/"+id, {
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

  function createTableBody(table, todoRecord) {
    for(let todoItem of todoRecord) {
        let row = table.insertRow();
        for(values in todoItem) {
            console.log(todoItem[values]);
            let cell = row.insertCell()
            let text = document.createTextNode(todoItem[values]);
            cell.appendChild(text);

        }
        
        let cellUpdate = row.insertCell();
        let updateBtn = document.createElement("a");
        let updateText = document.createTextNode("UPDATE");
        updateBtn.className = "btn btn-info";
        updateBtn.href = 'taskview.html?id='+todoItem.id
        updateBtn.appendChild(updateText);
        cellUpdate.appendChild(updateBtn);

        let cellDell = row.insertCell();
        let DelButton = document.createElement("a");
        let DelText = document.createTextNode("Delete");
        DelButton.className = "btn btn-danger";
        DelButton.onclick = function(){
          deltodo(todoItem.id);
          return false;
        };
        DelButton.appendChild(DelText);
        cellDell.appendChild(DelButton);
    }
  }

  function deltodo(id){
    fetch("http://localhost:8901/task/delete/"+id, {
        method: 'delete',
        headers: {
          "Content-type": "application/json"
        },
      })
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        let deldiv = document.getElementById("deldiv");
        deldiv.className ="alert alert-danger"
        deldiv.textContent ="Task Deleted";
        window.location.reload();
        
      })
      .catch(function (error) {
        console.log('Request failed', error);
        let deldiv = document.getElementById("create");
        deldiv.className ="alert alert-success"
        deldiv.textContent ="Error deleting student";
      });
}
