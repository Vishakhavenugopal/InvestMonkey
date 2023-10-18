var express = require('express');
const axios = require('axios');
var router = express.Router();




/* Client Login */
router.post('/login', function(req, res, next) {

    var loginRequest = req.body;

    clientLogin(loginRequest).then(data => {
        res.send(data);
    }).catch(err => res.sendStatus(err.response.status));
});

/* Client Register */
router.post('/register', function(req, res, next) {

    var registerRequest = req.body;

    clientRegister(registerRequest).then(data => {
        res.send(data);
    }).catch(err => res.sendStatus(err.response.status));
});


router.get('/pref/:id', function(req,res,next)
{
    console.log("Received get pref request: ",req.params.id);

    try{
    getPref(req.params.id).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.get('/clientBalance/:id', function(req,res,next)
{
    console.log("Received get pref request: ",req.params.id);

    try{
    getClientBalance(req.params.id).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.put('/pref', function(req,res,next)
{
    var updatedPref = req.body;

    try{
    putPref(updatedPref).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.post('/pref', function(req,res,next)
{
    var postRequest = req.body;

    try{
    postPref(postRequest).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.get('/holdings/:id', function(req,res,next)
{
    console.log("Received get holdings request: ",req.params.id);

    try{
    getHoldings(req.params.id).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.get('/prices', function(req,res,next)
{
    try{
    getPrices().then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.post('/trade', function(req,res,next)
{
    var postRequest = req.body;

    try{
    postTrade(postRequest).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.get('/tradeHistory/:id', function(req,res,next)
{
    console.log("Received get tradeHistory request: ",req.params.id);

    try{
    getTradeHistory(req.params.id).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});

router.get('/roboAdvisor/:id', function(req,res,next)
{
    console.log("Received get Robo Advisor request: ",req.params.id);

    try{
    getRoboAdvice(req.params.id).then(data => {
        res.send(data);
    });
}catch (error) {
    if (error.response) {
    res.status(error.response.status);
    res.send(error.response.data);
    } else {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
    }
}

});


async function getClientBalance(id) {
    return axios.get('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/clientBalance/'+id)
    .then(response => {
        console.log(response.data);
        return response.data
    });
}

async function getRoboAdvice(id) {
    return axios.get('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/roboAdvisor/'+id)
    .then(response => {
        console.log(response.data);
        return response.data
    });
}

async function getTradeHistory(id) {
    return axios.get('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/tradeHistory/'+id)
    .then(response => {
        console.log(response.data);
        return response.data
    });
}

async function postTrade(postRequest) {
    return axios.post('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/trade', postRequest)
    .then(response => {
        console.log(response.data);
        return response.data
    });
};

async function getHoldings(id) {
    return axios.get('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/holdings/'+id)
    .then(response => {
        console.log(response.data);
        return response.data
    });
}

async function getPrices() {
    return axios.get('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/prices')
    .then(response => {
        console.log(response.data);
        return response.data
    });
};

async function postPref(postRequest) {
    return axios.post('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/pref', postRequest)
    .then(response => {
        console.log(response.data);
        return response.data
    });
};

async function putPref(updatedPref) {
    return axios.put('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/pref', updatedPref)
    .then(response => {
        console.log(response.data);
        return response.data
    });
};

async function getPref(id) {
    return axios.get('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/pref/'+id)
    .then(response => {
        console.log(response.data);
        return response.data
    });
}

async function clientLogin(loginRequest) {
    return axios.post('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/login', loginRequest)
    .then(response => {
        console.log(response.data);
        return response.data
    });
};

async function clientRegister(registerRequest) {
    return axios.post('http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:8080/client/register', registerRequest)
    .then(response => {
        console.log(response.data);
        return response.data
    });
};

module.exports = router;