const WsClient = {
    /**
     * 初始化Stomp客户端封装对象
     * @param url 连接地址
     * @param options 配置项
     */
    init(url, options={}){
        if(!SockJS || !Stomp){
            console.error("请先引入SockJS和StompJS");
            return;
        }

        const socket = new SockJS(url);
        const client = Stomp.over(socket);

        //用自定义的参数覆盖默认参数
        const {
            debug=true, //是否开启调试，默认开启
            subscribes=[
                /*{
                    headers:{
                        //自定义头信息
                    },
                    dest: "", //订阅地址
                    handler(frame){//订阅成功后，接收消息的回调
                        console.log(frame);
                    }
                }*/
            ] //订阅列表，默认不订阅
        } = options;
        if(debug !== true){
            //关闭调试
            client.debug = null;
        }

        return {
            /**
             * Stomp客户端
             */
            client,
            /**
             * 订阅列表
             */
            subscribes: [],
            /**
             * 是否连接成功
             */
            connected: false,
            /**
             * 发起连接
             * @param headers 请求头信息
             */
            connect(headers={}){
                this.client.connect(
                    headers,
                    () => {//连接成功回调
                        console.log("WS服务连接成功");
                        //标记连接已建立
                        this.connected = true;
                        //订阅消息
                        this.subscribe(...subscribes)
                    },
                    error => {//连接失败回调
                        console.error("WS服务连接失败：" + error);
                    }
                );
                return this;
            },

            /**
             * 订阅
             * @param subscribes 订阅列表
             */
            subscribe(...subscribes){
                if(this.connected){
                    return this.doSubscribe(subscribes);
                }else{
                    //连接建立（因为是异步的）之前需用定时器等待
                    let interval = setInterval(()=>{
                        console.log("正在等待订阅："+(this.connected?"已连接":"未连接"))
                        if(this.connected){
                            this.doSubscribe(subscribes);
                            //清除定时器
                            clearInterval(interval);
                            interval = null;
                        }
                    }, 300);
                }
                return this;
            },
            doSubscribe(subscribes=[]){
                if(this.connected){
                    for(let subscribe of subscribes){
                        this.subscribes.push(subscribe);
                        let {dest, handler, headers={} } = subscribe;
                        //subscribe(destination, messageCallback, headers)
                        this.client.subscribe(dest, frame => {
                            handler && handler(frame);
                        }, headers);
                    }
                }else{
                    console.error("连接尚未建立，不可订阅")
                }
                return this;
            },
            /**
             * 发送消息
             * @param dest 目标地址
             * @param body 消息内容
             * @param headers 请求头信息
             */
            send(dest, body, headers={}){
                //send(destination, headers, body)
                this.client.send(dest, headers, body);
                return this;
            },
            /**
             * 断开连接
             */
            disconnect() {
                this.client.disconnect();
                console.log("连接已关闭");
                return this;
            }
        };
    }
};