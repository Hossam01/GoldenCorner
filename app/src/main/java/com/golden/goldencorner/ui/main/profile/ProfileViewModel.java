package com.golden.goldencorner.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.ProfileResponse;
import com.golden.goldencorner.data.model.SimpleResponse;
import com.golden.goldencorner.data.model.User;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.golden.goldencorner.data.Utils.AppConstant.ACCESS_TOKEN;
import static com.golden.goldencorner.data.Utils.AppConstant.Name;
import static com.golden.goldencorner.data.Utils.AppConstant.UserActivation;
import static com.golden.goldencorner.data.Utils.AppConstant.UserAvatar;
import static com.golden.goldencorner.data.Utils.AppConstant.UserCity;
import static com.golden.goldencorner.data.Utils.AppConstant.UserDescription;
import static com.golden.goldencorner.data.Utils.AppConstant.UserDeviceToken;
import static com.golden.goldencorner.data.Utils.AppConstant.UserEmail;
import static com.golden.goldencorner.data.Utils.AppConstant.UserId;
import static com.golden.goldencorner.data.Utils.AppConstant.UserMobile;
import static com.golden.goldencorner.data.Utils.AppConstant.UserName;
import static com.golden.goldencorner.data.Utils.AppConstant.UserPoint;
import static com.golden.goldencorner.data.Utils.AppConstant.UserType;

public class ProfileViewModel extends ViewModel {


    public User getDataFromShared() {
        User user = new User();
        user.setUserId(SharedPreferencesManager.getLong(UserId));
        user.setUsername(SharedPreferencesManager.getString(UserName));
        user.setName(SharedPreferencesManager.getString(Name));
        user.setActivate(SharedPreferencesManager.getLong(UserActivation));
        user.setEmail(SharedPreferencesManager.getString(UserEmail));
        user.setCity(SharedPreferencesManager.getString(UserCity));
        user.setAvatar(SharedPreferencesManager.getString(UserAvatar));
        user.setDescription(SharedPreferencesManager.getString(UserDescription));
        user.setDeviceToken(SharedPreferencesManager.getString(UserDeviceToken));
        user.setMobile(SharedPreferencesManager.getString(UserMobile));
        user.setPoint(SharedPreferencesManager.getLong(UserPoint));
        user.setUserType(SharedPreferencesManager.getLong(UserType));
        return user;
    }

    public void saveDataToShared(User user) {
        try {
            SharedPreferencesManager.put(UserId, user.getUserId());
            SharedPreferencesManager.put(UserName, user.getUsername());
            SharedPreferencesManager.put(Name, user.getName());
            SharedPreferencesManager.put(UserEmail, user.getEmail());
            SharedPreferencesManager.put(UserCity, user.getCity());
            SharedPreferencesManager.put(UserAvatar, user.getAvatar());
            SharedPreferencesManager.put(UserDescription, user.getDescription());
            SharedPreferencesManager.put(UserDeviceToken, user.getDeviceToken());
            SharedPreferencesManager.put(UserMobile, user.getMobile());
        } catch (Exception e) {
            Log.i("abood", "saveDataToShared: " + e.getMessage());
        }
//        SharedPreferencesManager.put(UserFamilyName, user.getFamilyName());
//        SharedPreferencesManager.put(UserAddress, user.getAddress());
//        SharedPreferencesManager.put(UserPoint, user.getPoints());
//        SharedPreferencesManager.put(UserType, user.getUser_type());
    }

//    (@Query("access-token") String access_token,
//    @Field("name") String name,
//    @Field("family_name") String family_name,
//    @Field("email") String email,
//    @Field("city") String city,
//    @Field("region") String region,
//    @Field("address") String address);

    public Flowable<ProfileResponse> updateProfileData(String name,
                                                       String family_name,
                                                       String email,
                                                       String city,
                                                       String region,
                                                       String address) {
        return RetrofitProvider.getClient().editProfile(
                SharedPreferencesManager.getString(ACCESS_TOKEN),
                name,
                family_name,
                email,
                city,
                region,
                address
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<ProfileResponse> editPassword(String password) {
        return RetrofitProvider.getClient().editPassword(
                SharedPreferencesManager.getString(ACCESS_TOKEN),
                password
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<ProfileResponse> editMobile(String mobile) {
        return RetrofitProvider.getClient().editMobile(
                SharedPreferencesManager.getString(ACCESS_TOKEN),
                mobile
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<SimpleResponse> resetPassword(String mobile) {
        return RetrofitProvider.getClient().passwordReset(
                mobile
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<SimpleResponse> passwordReset(String mobile, String token) {
        return RetrofitProvider.getClient().resetPassword(
                mobile, token
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


//    public void permissionsObserver(final EditProfileActivity mFragment) {
//        String[] permissions = {
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//        };
//        new RxPermissions(mFragment)
//                .request(permissions)
//                .subscribe(new DisposableObserver<Boolean>() {
//                    @Override
//                    public void onNext(Boolean granted) {
//                        if (granted) {
//                            // All requested permissions are granted
//                            LogUtils.e(TAG, "PERMISSIONS ARE GRANTED");
//                            isPermissionsGranted = true;
//                            showImageChooser(mFragment);
//                        } else {
//                            // At least one permission is denied
//                            utils.showToast(mFragment, mFragment.getString(R.string.grant_camera_and_storage_permissions));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtils.e(TAG, e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }
//
//    private void showImageChooser(EditProfileActivity mFragment) {
//        String positiveButtonText = "";
//        String negativeButtonText = "";
//        AlertDialog.Builder builder = new AlertDialog.Builder(mFragment
//                , R.style.AlertDialogTheme);
//        builder.setCancelable(true);
//        builder.setTitle(mFragment.getString(R.string.photo_action));
//        builder.setMessage(mFragment.getString(R.string.select_or_capture_image));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setPositiveButtonIcon(mFragment.getDrawable(R.drawable.ic_camera));
//            builder.setNegativeButtonIcon(mFragment.getDrawable(R.drawable.ic_gallery));
//        } else {
//            positiveButtonText = mFragment.getString(R.string.camera);
//            negativeButtonText = mFragment.getString(R.string.gallery);
//        }
//        builder.setNegativeButton(negativeButtonText,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        onPickPhotoFromGallery(mFragment);
//                    }
//                });
//        builder.setPositiveButton(positiveButtonText,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        onCapturePhotoFromCamera(mFragment);
//                    }
//                });
//        builder.create().show();
//    }
//
//
//    //    private final String APP_TAG = "PlayNow";
//    private final static int RC_PICK_IMAGE = 101;
//    private final static int RC_CAPTURE_IMAGE = 100;
//    private String photoFileName = "IMG_PROFILE.jpeg";
//
//    //open Camera to capture photo
//    private void onCapturePhotoFromCamera(EditProfileActivity mFragment) {
//        try {
//            // Create a File reference to access to future access
//            File photoFile = getPhotoFileUri(mFragment);
////        Uri fileProvider = FileProvider.getUriForFile(mFragment.getActivity(),
////                mFragment.getActivity().getPackageName().concat(".provider"), photoFile);
//            // create Intent to take a picture and return control to the calling application
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            //intent.getStringExtra(MediaStore.EXTRA_OUTgetString, fileProvider);
//            intent.getStringExtra(MediaStore.EXTRA_OUTgetString, photoFile);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            }
//            if (intent.resolveActivity(mFragment.getPackageManager()) != null) {
//                // Start the image capture intent to take photo
//                mFragment.startActivityForResult(intent, RC_CAPTURE_IMAGE);
//            }
//        } catch (Exception e) {
//            LogUtils.e(TAG, e.getMessage());
//        }
//    }
//
//    // open gallery selection for a photo
//    private void onPickPhotoFromGallery(EditProfileActivity mFragment) {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        if (intent.resolveActivity(mFragment.getPackageManager()) != null) {
//            mFragment.startActivityForResult(Intent.createChooser(intent, mFragment.
//                    getString(R.string.select_picture)), RC_PICK_IMAGE);
//        }
//    }
//
//    // save photo to directory
//    private File saveImage(Bitmap myBitmap, EditProfileActivity mFragment) {
//        try {
//            File destFile = getPhotoFileUri(mFragment);
//            ByteArrayOutgetStringStream bytes = new ByteArrayOutgetStringStream();
//            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            FileOutgetStringStream fos = new FileOutgetStringStream(destFile);
//            fos.write(bytes.toByteArray());
//            //fos.flush();
//            fos.close();
//            Log.d(TAG, "File Saved ---> " + destFile.getAbsolutePath());
//            return destFile;
//        } catch (IOException e) {
//            LogUtils.e(TAG, e.getMessage());
//            return null;
//        }
//    }
//
//    // Returns the File for a photo stored on disk
//    public File getPhotoFileUri(EditProfileActivity mFragment) {
//        try {
//            File mediaStorageDir = new File(mFragment.
//                    getExternalFilesDir(Environment.DIRECTORY_PICTURES).toURI()/*, APP_TAG*/);
//            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
//                mediaStorageDir.mkdirs();
//            }
//            // Return the file target for the photo based on filename
//            File file = new File(mediaStorageDir, photoFileName);
//            if (!file.exists())
//                file.createNewFile();
//            return file;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data, CircleImageView userImage,
//                                    EditProfileActivity mFragment) {
//        try {
//            if (resultCode == RESULT_CANCELED) {
//                return;
//            }
//            if (requestCode == RC_CAPTURE_IMAGE) {
//                if (/*resultCode == RESULT_OK &&*/ data != null) {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    userImage.setImageBitmap(bitmap);
//                    File path = saveImage(bitmap, mFragment);
//                    LogUtils.e(TAG, path.toString());
////                    if (path != null)
////                        provideImageLoader(path, userImage);
//                }
//            } else if (requestCode == RC_PICK_IMAGE) {
//                if (data != null) {
//                    Uri contentURI = data.getData();
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mFragment.getContentResolver(), contentURI);
//                    userImage.setImageBitmap(bitmap);
//                    File path = saveImage(bitmap, mFragment);
//                    LogUtils.e(TAG, path.toString());
////                    if (path != null)
////                        provideImageLoader(path, userImage);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeFileFromStorage(String filePath) {
//        File file = new File(filePath);
//        if (file.exists()) {
//            file.delete();
//        }
//    }
//
//    public boolean getStringUserRecordToSharedPreferences(UserRecord userRecord) {
//        return sessionManager.getStringUserRecordToSharedPreferences(userRecord);
//    }
//
//    public UserRecord convertUserRecord(List data) {
//        return responseConverters.convertUserRecord(data);
//    }
//
//    public void provideImageLoader(File photoFile, CircleImageView userImage) {
//        utils.provideImageLoader(photoFile, userImage);
//    }
//
//    public void provideImageLoader(String imageUrl, CircleImageView userImage) {
//        utils.provideImageLoader(imageUrl, userImage);
//    }
//
//    public String getSharedPreferences(String key) {
//        return sharedPreferencesManager.getString(key);
//    }
//
//    public Flowable<UserSettingResponse> getUserSettings() {
//        return remoteRepository.getRemoteApi().getUserSetting().subscribeOn(Schedulers.io());
//    }
//
//    public Flowable<UserSettingResponse> addUserSettings(SettingModel settingModel) {
//        return remoteRepository.getRemoteApi().addUserSetting(settingModel).subscribeOn(Schedulers.io());
//    }
//
//    public Flowable<LoginResponse> updateUserProfile(String userNameSP, String userEmailSP, String userPasswordSP, File userImagePath) {
//        MultipartBody.Part imageBody;
//        if (userImagePath != null) {
//            //File newFile = new File(userImagePath);
//            imageBody = MultipartBody.Part.createFormData(FORM_IMAGE, userImagePath.getName(),
//                    RequestBody.create(MediaType.parse("image/*"), userImagePath));
//        } else {
//            imageBody = null;
//        }
//
//        Map<String, RequestBody> params = new HashMap<>();
//        params.getString(NAME, RequestBody.create(MultipartBody.FORM, userNameSP));
//        params.getString(EMAIL, RequestBody.create(MultipartBody.FORM, userEmailSP));
//        params.getString(PPASSWORD, RequestBody.create(MultipartBody.FORM, userPasswordSP));
//
//        return remoteRepository.getRemoteApi().getEditUserProfile(params, imageBody).subscribeOn(Schedulers.io());
//    }


}