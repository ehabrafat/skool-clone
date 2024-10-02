# Skool Clone
## Features
- CRUD communities, posts and comments
- Authentication using JWT tokens
- real-time chat using stomp message brokers
- Dynamic filtering and pagination system for communities
- Implemented social media style comments pagination

## Sample of APIS
#### Get all communties
```
GET /communities?type=public&price=free&pageNum=0&pageSize=10

type: public or private.

price: free or paid

pageNum and pageSize for pagination
```
#### Get community prerequisite questions
```
GET /communities/{id}/questions
```

#### Join Community
```
POST /communities/{id}/join
body: [
  {
    "questionId": "int",
    "answer": "string"
  }
]
```

### Get posts comments
```
GET /communities/{id}/posts/{postId}/comments?pageNum=0&pageSize=1&replyTo=null

- Pagination: Fetches comments with pagination controls via pageNum and pageSize.
- Replies: Returns up to 2 replies per comment along with a count of additional replies.
- Expandable replies: To retrieve all replies for a specific comment, use the replyTo parameter with the comment's commentId.

For example
{
    "content": [
        {
            "id": 1,
            "content": "This is an amazing post! Thanks for sharing.",
            "edited": false,
            "likes": 0,
            "replies": {
                "content": [
                    {
                        "id": 6,
                        "content": "I totally agree!",
                        "edited": false,
                        "likes": 2,
                        "hasMoreRepliesCount": 1
                    },
                    {
                        "id": 9,
                        "content": "Same here, great insights.",
                        "edited": false,
                        "likes": 0,
                        "hasMoreRepliesCount": 0
                    }
                ],
                "numberOfElements": 2,
                "hasMoreCommentsCount": 2,
                "hasNext": true,
                "hasPrevious": false
            }
        }
    ],
    "numberOfElements": 0,
    "hasMoreCommentsCount": 6,
    "hasNext": true,
    "hasPrevious": false
}
```
### Chat
Use stompjs lib to subscribe to topics, send and receive messages
```
  const sock = new SocketJS("http://localhost:8080/ws");
  const stompClient = over(sock);
  stompClient.connect({}, () => {
    stompClient.subscribe("/topic/public", (msg) => {
      console.log("Received " + msg.body);
    });
    stompClient.subscribe("/topic/error", (msg) => {
      console.log("Received error " + msg.body);
    });
    stompClient.subscribe("/user/{userId}/private", (msg) => {
      console.log("private message " + msg.body);
    });
    let message = {
      content: "Good Morning!",
      receiverId: 3,
    };
    stompClient.send("/app/messages", {}, JSON.stringify(message));
  });
```
