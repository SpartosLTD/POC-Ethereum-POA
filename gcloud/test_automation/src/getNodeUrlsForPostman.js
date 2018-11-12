const util = require('util');
const exec = util.promisify(require('child_process').exec);

const protocol = "http";
const port = 8500;

const group = process.env.GROUP;

async function saveInstancesToFile() {
    const fileName = group + '.txt';
    const command = "gcloud compute instances list | grep " + group + " > " + fileName;
    await exec(command);
    return fileName;
}

async function getIps(file) {
    return new Promise(function (resolve, reject) {
            var lineReader = require('readline').createInterface({
                input: require('fs').createReadStream(file)
            });

            var bootNodeIp = null;
            const nodeIps = [];

            lineReader.on('line', function (line) {
                if (line.indexOf('bootstrap') >= 0) {
                    bootNodeIp = getExternalIp(line);
                } else  {
                    nodeIps.push(getExternalIp(line));
                }
                console.log(line);
            });
            lineReader.on('close', () => {
                resolve({
                    bootNodeIp,
                    nodeIps
                });
            });
        }
    )
}

function getNodeUrlsForPostman(nodeIps) {
    return "[" + nodeIps.map(ip => "\"" + protocol + "://" + ip + ":" + port + "\"") + "]";
}

function getExternalIp(src) {
    var columns = src.split(/\s+/);
    console.log(columns.length);
    return columns[4];
}

async function start() {
    const fileName = await saveInstancesToFile();
    console.log("saved to file " + fileName);
    const ips = await getIps(fileName);
    console.log("j%", ips);
    const result = getNodeUrlsForPostman(ips.nodeIps);
    console.log("j%", result);
    await exec("rm " + fileName);
}

start();