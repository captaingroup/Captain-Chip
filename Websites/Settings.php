<html>
<head>
  <title>Settings</title>
  <link rel="stylesheet" href="foundation-5.1.1/css/foundation.css">
</head>
<body>

  <div class="row">
    <div class="small-10 columns">
      <h1>Captain</h1>
    </div>  
  </div>

  <div class="row">

    <dl class="vertical tabs">
      <dd class="active"><a href="#vertical1">Sensors</a></dd>
      <dd><a href="#vertical2">Settings 2</a></dd>
      <dd><a href="#vertical3">Settings 3</a></dd>
      <dd><a href="#vertical3">Settings 4</a></dd>
    </dl>

    <div class="small-9 columns">
      <h3>Connected sensors</h3>

      <table>
        <thead>
          <tr>
            <th>Captain Number</th>
            <th>Patient ID</th>
            <th>Device name</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
              <tr>
      <td>a</td>
      <td>b</td>
      <td>c</td>
      <td>d</td>
      <td>e</td>
    </tr>
          <?php
          $con=mysqli_connect("sql3.freemysqlhosting.net","sql331497","sI2*yG2*","sql331497");
      // Check connection
          if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
          }

          $result = mysqli_query($con,"SELECT DISTINCT `Captain Number`, 
            `Patient ID`, `Device Name` FROM `4-Sensors`");

          while($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<td>" . $row['Captain Number'] . "</td>";
            echo "<td>" . $row['Patient ID'] . "</td>";
            echo "<td>" . $row['Device name'] . "</td>";
            echo "<td>Active</td>";
            echo "<td> <a href='#'>Remove </td>";
            echo "</tr>";
          }

          mysqli_close($con);
          ?>
        </tbody>
      </table>
      <h3>Add sensor</h3>



      <table>
        <thead>
          <tr>
            <th>Sensor ID</th>
            <th>Type</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>3684930284</td>
            <td>oxygen saturation monitor</td>
            <td>Inactive</td>
            <td><a href='#'>Pair</td>
          </tr>

        </tbody>
      </table>
    </div>
  </div>
</div>
</div>
</body>
</html>