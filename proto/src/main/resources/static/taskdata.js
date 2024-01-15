[ {
    "Id" : 1,
    "Name" : "Planning",
    "PercentDone" : 50,
    "Priority" : 1,
    "Responsible" : "",
    "StartDate" : "2010-01-18",
    "Duration" : 10,
    "expanded" : true,
    "children" : [
        {
            "Id" : 11,
            "leaf" : true,
            "Name" : "Investigate",
            "PercentDone" : 50,
            "Priority" : 1,
            "Responsible" : "",
            "StartDate" : "2010-01-18",
            "Duration" : 8
        },
        {
            "Id" : 12,
            "leaf" : true,
            "Name" : "Assign resources",
            "PercentDone" : 0,
            "Priority" : 0,
            "Responsible" : "",
            "StartDate" : "2010-01-27",
            "Duration" : 3
        },
        {
            "Id" : 13,
            "leaf" : true,
            "Name" : "Gather documents",
            "PercentDone" : 80,
            "Priority" : 1,
            "Responsible" : "",
            "StartDate" : "2010-01-24",
            "Duration" : 5
        },
        {
            "Id" : 17,
            "leaf" : true,
            "Name" : "Report to management",
            "PercentDone" : 0,
            "Priority" : 0,
            "Responsible" : "",
            "StartDate" : "2010-01-29",
            "Duration" : 0
        }
    ]
  },
  {
    "Id" : 4,
    "Name" : "Implementation Phase 1",
    "PercentDone" : 40,
    "Priority" : 1,
    "Responsible" : "",
    "StartDate" : "2010-01-25",
    "Duration" : 40,
    "expanded" : true,
    "children" : [{
            "Id" : 34,
            "leaf" : true,
            "Name" : "Preparation work",
            "PercentDone" : 40,
            "Priority" : 0,
            "Responsible" : "",
            "StartDate" : "2010-01-25",
            "Duration" : 5
        },
        {
            "Id" : 14,
            "leaf" : true,
            "Name" : "Evaluate chipsets",
            "PercentDone" : 40,
            "Priority" : 0,
            "Responsible" : "",
            "StartDate" : "2010-02-25",
            "Duration" : 7
        },
        {
            "Id" : 16,
            "leaf" : true,
            "Name" : "Choose technology suite",
            "PercentDone" : 40,
            "Priority" : 0,
            "Responsible" : "",
            "StartDate" : "2010-03-10",
            "Duration" : 8
        },
        {
            "Id" : 15,
            "Name" : "Build prototype",
            "PercentDone" : 40,
            "Priority" : 0,
            "Responsible" : "",
            "StartDate" : "2010-01-30",
            "Duration" : 25,
            "children" : [
                {
                    "Id" : 20,
                    "leaf" : true,
                    "Name" : "Step 1",
                    "PercentDone" : 40,
                    "Priority" : 1,
                    "Responsible" : "",
                    "StartDate" : "2010-01-30",
                    "Duration" : 6
                },
                {
                    "Id" : 19,
                    "leaf" : true,
                    "Name" : "Step 2",
                    "PercentDone" : 40,
                    "Priority" : 1,
                    "Responsible" : "",
                    "StartDate" : "2010-02-17",
                    "Duration" : 3
                },
                {
                    "Id" : 18,
                    "leaf" : true,
                    "Name" : "Step 3",
                    "PercentDone" : 40,
                    "Priority" : 1,
                    "Responsible" : "",
                    "StartDate" : "2010-02-25",
                    "Duration" : 7
                  },
                  {
                    "Id" : 21,
                    "leaf" : true,
                    "Name" : "Follow up with customer",
                    "PercentDone" : 40,
                    "Priority" : 0,
                    "Responsible" : "",
                    "StartDate" : "2010-03-04",
                    "Duration" : 2
                  }
            ]
        }
    ]
  },
    {
        "Id" : 5,
        "leaf" : true,
        "Name" : "Customer approval",
        "PercentDone" : 0,
        "Priority" : 2,
        "Responsible" : "",
        "StartDate" : "2010-03-08",
        "Duration" : 0
    },
    {
        "Id" : 6,
        "Name" : "Implementation Phase 2",
        "PercentDone" : 20,
        "Priority" : 1,
        "Responsible" : "",
        "StartDate" : "2010-03-08",
        "Duration" : 8,
        "expanded" : true,
        "children" : [
            {
                "Id" : 25,
                "leaf" : true,
                "Name" : "Task 3",
                "PercentDone" : 20,
                "Priority" : 0,
                "Responsible" : "",
                "StartDate" : "2010-03-08",
                "Duration" : 8
            },
            {
                "Id" : 26,
                "leaf" : true,
                "Name" : "Task 2",
                "PercentDone" : 20,
                "Priority" : 0,
                "Responsible" : "",
                "StartDate" : "2010-03-08",
                "Duration" : 8
            },
            {
                "Id" : 27,
                "leaf" : true,
                "Name" : "Task 1",
                "PercentDone" : 20,
                "Priority" : 0,
                "Responsible" : "",
                "StartDate" : "2010-03-08",
                "Duration" : 8
            }
        ]
    },
    {
        "Id" : 10,
        "leaf" : true,
        "Name" : "Customer approval 2",
        "PercentDone" : 0,
        "Priority" : 1,
        "Responsible" : "",
        "StartDate" : "2010-03-17",
        "Duration" : 0
    },
    {
        "Id" : 8,
        "Name" : "Production phase 1",
        "PercentDone" : 45,
        "Priority" : 2,
        "Responsible" : "",
        "StartDate" : "2010-03-22",
        "Duration" : 35,
        "expanded" : true,
        "children" : [
            {
                "Id" : 22,
                "leaf" : true,
                "Name" : "Assemble",
                "PercentDone" : 45,
                "Priority" : 1,
                "Responsible" : "",
                "StartDate" : "2010-03-22",
                "Duration" : 12
            },
            {
                "Id" : 23,
                "leaf" : true,
                "Name" : "Load SW",
                "PercentDone" : 45,
                "Priority" : 2,
                "Responsible" : "",
                "StartDate" : "2010-04-06",
                "Duration" : 11
            },
            {
                "Id" : 24,
                "leaf" : true,
                "Name" : "Basic testing (inc some test)",
                "PercentDone" : 45,
                "Priority" : 2,
                "Responsible" : "",
                "StartDate" : "2010-04-22",
                "Duration" : 12
            }
        ]
    },
    {
        "Id" : 9,
        "leaf" : true,
        "Name" : "Final testing",
        "PercentDone" : 0,
        "Priority" : 1,
        "Responsible" : "",
        "StartDate" : "2010-05-07",
        "Duration" : 6
    },
    {
        "Id" : 7,
        "leaf" : true,
        "Name" : "Delivery",
        "PercentDone" : 40,
        "Priority" : 1,
        "Responsible" : "",
        "StartDate" : "2010-05-14",
        "Duration" : 0
    }
]
