export const convertSeconds = (seconds) => {
    
    switch(true) {
      case seconds <= 60:
        return fixNumber(seconds) + " seconds"
      case seconds > 60 && seconds < 3600:
        let minutes = seconds / 60;
        return fixNumber(minutes) + " minutes"
      default:
        let hours = seconds / 3600;
        return fixNumber(hours) + " hours"
    }

  };

  const fixNumber = (number) => {
    return Number(number).toFixed(1)
  }
  