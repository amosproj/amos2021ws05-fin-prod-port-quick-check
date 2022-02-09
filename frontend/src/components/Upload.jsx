import React, { useState, useRef } from 'react';

import { IconButton } from '@chakra-ui/react';
import { notification } from '../utils/notification';
import { AttachmentIcon } from '@chakra-ui/icons';

export default function UploadButton(buttonProps) {
  const [file, setFile] = useState('');
  const inputFile = useRef(null);

  const handleFileUpload = (e) => {
    const { files } = e.target;
    if (files && files.length) {
      const filename = files[0].name;

      notification('Uploading...', `file: ${filename}`, 'info');
      setFile(files[0]);
      // send_to_backend(file)
    }
  };

  const onButtonClick = () => {
    inputFile.current.click();
  };

  return (
    <div>
      <input style={{ display: 'none' }} ref={inputFile} onChange={handleFileUpload} type="file" />
      <IconButton {...buttonProps} icon={<AttachmentIcon onClick={onButtonClick} />} />
    </div>
  );
}
