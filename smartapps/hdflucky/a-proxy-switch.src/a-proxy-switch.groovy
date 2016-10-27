definition(
    name:	"A Proxy Switch", namespace: "HDFLucky", author: "Bruce Ravenel",
    description: "Links a virtual switch to a real one, so that if physical switch is already on, will only be turned off manually.",
    category:	"My Apps",
    iconUrl:	"http://cdn.device-icons.smartthings.com/Lighting/light13-icn.png",
    iconX2Url:	"http://cdn.device-icons.smartthings.com/Lighting/light13-icn@2x.png",
    iconX3Url:	"http://cdn.device-icons.smartthings.com/Lighting/light13-icn@2x.png")
preferences {
    section("Physical Switch") { 
        input "physical", "capability.switch", 
	title: "Real switch… ", 
        required: true
    }
    section("Virtual Switch") {
    	input "virtual", "capability.switch",
        title: "Proxy switch… ",
        required: true
    }
}
def installed() {
    subscribe(virtual, "switch.on", switchOnHandler)
    subscribe(virtual, "switch.off", switchOffHandler)
}
def updated() {
    unsubscribe()
    subscribe(virtual, "switch.on", switchOnHandler)
    subscribe(virtual, "switch.off", switchOffHandler)
}
def switchOnHandler(evt) {
    state.wasOff = physical.currentValue("switch") == "off"
    if(state.wasOff)physical.on()
}
def switchOffHandler(evt) {
    if(state.wasOff)physical.off()
}