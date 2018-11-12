const config = {
    instancesCount: 17,
    groupName: "nodes17",
    instanceConfig: {
        http: true,
        machineTytpe: "n1-standard-4",
        disks: [
            {
                boot: true,
                initializeParams: {
                    sourceImage: "projects/ubuntu-os-cloud/global/images/ubuntu-1604-xenial-v20180814"
                }
            }
        ],
    }
};


const zones = [
    "europe-west3-b",
    "europe-west3-a",
    "europe-west3-b",
    "europe-west3-c",
    "europe-west2-b",
    "europe-west2-c",
    "europe-west1-b",
    "europe-west1-c"
];


module.exports = {config, zones};