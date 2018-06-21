let baseUrl = ''; 
let routerMode = 'history';

if (process.env.NODE_ENV == 'development') {
	  baseUrl = 'http://localhost:8088';
}else{
	//部署时的生产地址
	  baseUrl = 'http://localhost:8088';
}

export {
	baseUrl,
	routerMode
}