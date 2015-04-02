
create DATABASE TaskLab;
use TaskLab;


CREATE TABLE groupDesc(
	groupID int NOT NULL AUTO_INCREMENT,
    groupName VARCHAR(100) NOT NULL,
    PRIMARY KEY (groupID)
);

select * from groupDesc;

CREATE TABLE userCred(
   userName VARCHAR(40) NOT NULL,
   password VARCHAR(40) NOT NULL,
   firstName VARCHAR(40) NOT NULL,
   lastName VARCHAR(40) NOT NULL,
   email VARCHAR(100) NOT NULL,
   groupID int NOT NULL,
   PRIMARY KEY (userName),
   FOREIGN KEY (groupID) REFERENCES groupDesc(groupID)
);

Select * from userCred;


CREATE TABLE taskDescription(
   taskID int NOT NULL AUTO_INCREMENT,
   taskName VARCHAR(40) NOT NULL,
   dueDate DATE,
   groupID int NOT NULL,
   taskPoints FLOAT,
   PRIMARY KEY (taskID),
   FOREIGN KEY (groupID) REFERENCES groupDesc(groupID)
   
);

Select * from taskDescription;





CREATE TABLE taskAssignment(
	taskID int NOT NULL,
    userName VARCHAR(40) NOT NULL,
    FOREIGN KEY (taskID) REFERENCES taskDescription(taskID),
    FOREIGN KEY (userName) REFERENCES userCred(userName)
);

    
Select * from taskAssignment;





    