fetch('http://localhost:8901/tasklist/readAll/')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(todoData) {
        console.log(todoData);

        let table = document.querySelector("table");
        let dataHead = Object.keys(todoData[0]);

        createTableBody(table, todoData);
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });

  function createTableBody(table, todoData) {
      for(let todoRecord of todoData) {
          let row = table.insertRow();
          for(values in todoRecord) {
            if(values === "tasks") {
              continue
            } else {
              console.log(todoRecord[values]);
              let cell = row.insertCell()
              let text = document.createTextNode(todoRecord[values]);
              cell.appendChild(text);
            }
          }
          
          let cellView = row.insertCell();
          let viewButton = document.createElement("a");
          let viewText = document.createTextNode("VIEW");
          viewButton.className = "btn btn-info";
          viewButton.href = 'record.html?id='+todoRecord.id
          viewButton.appendChild(viewText);
          cellView.appendChild(viewButton);
          
        let cellDell = row.insertCell();
        let DelButton = document.createElement("a");
        let DelText = document.createTextNode("Delete");
        DelButton.className = "btn btn-danger";
        DelButton.onclick = function(){
          deltodo(todoRecord.id);
          return false;
        };
        DelButton.appendChild(DelText);
        cellDell.appendChild(DelButton);
      }
  }

  function deltodo(id){
    fetch("http://localhost:8901/tasklist/delete/"+id, {
        method: 'delete',
        headers: {
          "Content-type": "application/json"
        },
      })
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        let deldiv = document.getElementById("deldiv");
        deldiv.className ="alert alert-danger"
        deldiv.textContent ="Task List Deleted";
        window.location.reload();
        
      })
      .catch(function (error) {
        console.log('Request failed', error);
        let deldiv = document.getElementById("create");
        deldiv.className ="alert alert-success"
        deldiv.textContent ="Error deleting student";
      });
}
