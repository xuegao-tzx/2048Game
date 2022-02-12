import router from '@system.router'
import app from '@system.app'
import brightness from '@system.brightness';
import vibrator from '@system.vibrator';
import prompt from '@system.prompt';
export default {
    onInit() {
        brightness.setKeepScreenOn({keepScreenOn: true,});
        brightness.setMode({mode: 1,});
    },
    onSwipe(e) {
        switch (e.direction) {
            case 'left':
                break;
            case 'right':
                break;
            case 'up':
                break;
            case 'down':
                break;
        }
    },
    ToStart(){
        vibrator.vibrate({
            mode: 'short',
            success: function(ret) {
                console.log('tzx vibrate is successful');
            },
            fail: function(ret) {
                console.log('tzx vibrate is failed');
            },
            complete: function(ret) {
                console.log('tzx vibrate is completed');
            }
        });
        prompt.showToast({
            message: '开始成功，By Xcl!',
            duration: 3579,
        });
        router.replace({
            uri: "pages/index/index",
        });
    },
    ToClose(){
        vibrator.vibrate({
            mode: 'short',
            success: function(ret) {
                console.log('tzx vibrate is successful');
            },
            fail: function(ret) {
                console.log('tzx vibrate is failed');
            },
            complete: function(ret) {
                console.log('tzx vibrate is completed');
            }
        });
        app.terminate();
    },
}
